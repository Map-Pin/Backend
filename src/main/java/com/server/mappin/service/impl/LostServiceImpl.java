package com.server.mappin.service.impl;

import com.server.mappin.converter.CategoryConverter;
import com.server.mappin.converter.LocationConverter;
import com.server.mappin.converter.LostConverter;
import com.server.mappin.converter.ShopConverter;
import com.server.mappin.domain.Category;
import com.server.mappin.domain.Location;
import com.server.mappin.domain.Lost;
import com.server.mappin.domain.Member;
import com.server.mappin.dto.Category.CategoryDTO;
import com.server.mappin.dto.Location.LocationDTO;
import com.server.mappin.dto.Lost.LostDTO;
import com.server.mappin.dto.Shop.ShopDTO;
import com.server.mappin.repository.CategoryRepository;
import com.server.mappin.repository.LocationRepository;
import com.server.mappin.repository.LostRepository;
import com.server.mappin.repository.MemberRepository;
import com.server.mappin.service.LostService;
import com.server.mappin.service.MapService;
import com.server.mappin.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LostServiceImpl implements LostService {
    private final LostRepository lostRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final MapService mapService;
    private final S3Service s3Service;

    @Override
    public CategoryDTO.CategoryListRP findByCategory(String categoryName) {
        List<Lost> losts = lostRepository.findByCategory(categoryName);
        return CategoryConverter.toCategoryList(losts);
    }

    @Override
    public LocationDTO.LocationListRP findByDong(String dongName) {
        List<Lost> dongs = lostRepository.findLocationByDong(dongName);
        return LocationConverter.toLocationList(dongs);
    }

    @Override
    public ShopDTO.ShopListRP findByShop(String shopName) {
        List<Lost> shops = lostRepository.findLostByShopName(shopName);
        return ShopConverter.toShopListResponse(shops);
    }

    @Override
    public LocationDTO.LocationListRP findByCurrentLocation(Double x, Double y) {
        List<Lost> locationByDong = lostRepository.findAll();
        return LocationDTO.LocationListRP.builder()
                .result(locationByDong.stream().map(lost -> LocationDTO.LocationRP.builder()
                                .id(lost.getId())
                                .title(lost.getTitle())
                                .createdAt(lost.getCreatedAt())
                                .imageUrl(lost.getImageUrl())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public LostDTO.LostRegisterRP registerLost(LostDTO.LostRegisterRQ lostRegisterRQ, MultipartFile image, String email) throws IOException{
        //String으로 받아온 yyyy-MM-dd를 LocalDate 형식으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(lostRegisterRQ.getFoundDate(),formatter);
        //회원, 카테고리 찾기
        Optional<Member> memberRepositoryByEmail = memberRepository.findByEmail(email);
        Optional<Category> categoryByName = categoryRepository.findCategoryByName(lostRegisterRQ.getCategory());
        //x,y좌표로 동네 찾고 동네가 DB에 존재하는지 확인
        String dong = mapService.getDong(lostRegisterRQ.getX(), lostRegisterRQ.getY());
        Optional<Location> locationByDong = locationRepository.findLocationByDong(dong);
        //이미지 S3에 업로드
        String imageUrl = s3Service.upload(image, "images");
        if(memberRepositoryByEmail.isPresent() && categoryByName.isPresent() && locationByDong.isPresent()){
            Member member = memberRepositoryByEmail.get();
            Location location = locationByDong.get();
            Category category = categoryByName.get();
            Lost lost = LostConverter.toLost(lostRegisterRQ,localDate,imageUrl,category,location,member);
            Lost save = lostRepository.save(lost);
            return LostConverter.toLostRegisterRP(save);
        }
        return LostDTO.LostRegisterRP.builder()
                .build();

    }

    @Override
    public LostDTO.LostSearchByIdRP getById(Long id){
        Optional<Lost> lostById = lostRepository.findById(id);
        return lostById
                .map(lost -> LostDTO.LostSearchByIdRP.builder()
                        .title(lost.getTitle())
                        .content(lost.getContent())
                        .x(lost.getX())
                        .y(lost.getY())
                        .foundDate(lost.getFoundDate())
                        .category(lost.getCategory().getName())
                        .dong(lost.getLocation().getDong())
                        .createdAt(lost.getCreatedAt())
                        .image(lost.getImageUrl())
                        .build())
                .orElse(LostDTO.LostSearchByIdRP.builder()
                        .build());
    }
    @Override
    @Transactional
    public LostDTO.LostUpdateRP update(Long lostId, LostDTO.LostUpdateRQ lostUpdateRQ, MultipartFile image, String email) throws IOException {
        Lost lost = lostRepository.findById(lostId).orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다"));
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isEmpty()){
            return LostDTO.LostUpdateRP.builder().build();
        }
        // 업데이트 필드를 확인하고 필요한 경우 업데이트
        if (lostUpdateRQ.getFoundDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(lostUpdateRQ.getFoundDate(), formatter);
            lost.setFoundDate(localDate);
        }

        if (lostUpdateRQ.getX() != null && lostUpdateRQ.getY() != null) {
            String dong = mapService.getDong(lostUpdateRQ.getX(), lostUpdateRQ.getY());
            Optional<Location> locationByDong = locationRepository.findLocationByDong(dong);
            locationByDong.ifPresent(lost::setLocation);
        }

        if (lostUpdateRQ.getCategory() != null) {
            Optional<Category> categoryByName = categoryRepository.findCategoryByName(lostUpdateRQ.getCategory());
            categoryByName.ifPresent(lost::setCategory);
        }

        if (lostUpdateRQ.getContent() != null) {
            lost.setContent(lostUpdateRQ.getContent());
        }

        if (lostUpdateRQ.getTitle() != null) {
            System.out.println("LOST = " + lostUpdateRQ.getTitle());
            lost.setTitle(lostUpdateRQ.getTitle());
        }

        if (image != null) {
            // 이미지 업로드 및 기존 이미지 삭제 처리
            if (!s3Service.findByUrl(lost.getImageUrl()).isEmpty()) {
                s3Service.delete(lost.getImageUrl());
            }
            String imageUrl = s3Service.upload(image, "images");
            lost.setImageUrl(imageUrl);
        }
        // 게시물 업데이트
        Lost updatedLost = lostRepository.save(lost);

        if (updatedLost != null) {
            return LostConverter.toLostUpdateRP(updatedLost);

        } else {
            return LostDTO.LostUpdateRP.builder()
                    .build();
        }
    }
}
