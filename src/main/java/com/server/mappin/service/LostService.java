package com.server.mappin.service;

import com.server.mappin.domain.Category;
import com.server.mappin.domain.Location;
import com.server.mappin.domain.Lost;
import com.server.mappin.domain.Member;
import com.server.mappin.dto.*;
import com.server.mappin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LostService {
    private final LostRepository lostRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final MapService mapService;
    private final S3Service s3Service;

  public FindByCategoryListResponseDto findByCategory(String categoryName) {
    List<Lost> losts = lostRepository.findByCategory(categoryName);

    return FindByCategoryListResponseDto.builder()
            .statusCode(200)
            .isSuccess("true")
            .losts(losts.stream().map(lost -> FindByCategoryResponseDto.builder()
                    .id(lost.getId())
                    .title(lost.getTitle())
                    .creatdAt(lost.getCreatedAt())
                    .imageUrl(lost.getImageUrl())
                    .build())
                    .collect(Collectors.toList()))
            .build();
  }

  public List<FindByDongResponseDto> findByDong(String dongName) {
    List<Lost> dongs = lostRepository.findLocationByDong(dongName);
    List<FindByDongResponseDto> result = dongs.stream().map(dong -> FindByDongResponseDto.builder().id(dong.getId()).title(dong.getTitle()).dong(dong.getLocation().getDong()).imageUrl(dong.getImageUrl()).createdAt(dong.getCreatedAt()).build()).collect(Collectors.toList());
    return result;
  }

  public List<FindByShopResponseDto> findByShop(String shopName) {
    List<Lost> shops = lostRepository.findLostByShopName(shopName);
    List<FindByShopResponseDto> result = shops.stream().map(shop -> FindByShopResponseDto.builder().id(shop.getId()).title(shop.getTitle()).shopName(shopName).imageUrl(shop.getImageUrl()).createdAt(shop.getCreatedAt()).build()).collect(Collectors.toList());
    return result;
  }

    @Transactional
    public LostRegisterResponseDto registerLost(LostRegisterRequestDto lostRegisterRequestDto, MultipartFile image, String email) throws IOException {
        //String으로 받아온 yyyy-MM-dd를 LocalDate 형식으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(lostRegisterRequestDto.getFoundDate(),formatter);
        //회원, 카테고리 찾기
        Optional<Member> memberRepositoryByEmail = memberRepository.findByEmail(email);
        Optional<Category> categoryByName = categoryRepository.findCategoryByName(lostRegisterRequestDto.getCategory());
        //x,y좌표로 동네 찾고 동네가 DB에 존재하는지 확인
        String dong = mapService.getDong(lostRegisterRequestDto.getX(), lostRegisterRequestDto.getY());
        Optional<Location> locationByDong = locationRepository.findLocationByDong(dong);
        //이미지 S3에 업로드
        String imageUrl = s3Service.upload(image, "images");
        if(memberRepositoryByEmail.isPresent() && categoryByName.isPresent() && locationByDong.isPresent()){
            Member member = memberRepositoryByEmail.get();
            Location location = locationByDong.get();
            Category category = categoryByName.get();
            Lost lost = Lost.builder()
                    .title(lostRegisterRequestDto.getTitle())
                    .content(lostRegisterRequestDto.getContent())
                    .x(lostRegisterRequestDto.getX())
                    .y(lostRegisterRequestDto.getY())
                    .foundDate(localDate)
                    .imageUrl(imageUrl)
                    .createdAt(LocalDateTime.now())
                    .category(category)
                    .location(location)
                    .member(member)
                    .build();
            Lost save = lostRepository.save(lost);
            return LostRegisterResponseDto.builder()
                    .statusCode(200)
                    .isSuccess("true")
                    .title(lost.getTitle())
                    .content(lost.getContent())
                    .x(lost.getX())
                    .y(lost.getY())
                    .foundDate(lost.getFoundDate())
                    .createdAt(lost.getCreatedAt())
                    .image(lost.getImageUrl())
                    .category(lost.getCategory().getName())
                    .memberId(lost.getMember().getId())
                    .dong(location.getDong())
                    .lostId(lost.getId())
                    .build();

        }
        return LostRegisterResponseDto.builder()
                .statusCode(400)
                .isSuccess("false")
                .build();

    }

    public LostSearchByIdResponseDto getById(Long id){
        Optional<Lost> lostById = lostRepository.findById(id);
        return lostById
                .map(lost -> LostSearchByIdResponseDto.builder()
                        .statusCode(200)
                        .isSuccess("true")
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
                .orElse(LostSearchByIdResponseDto.builder()
                        .statusCode(400)
                        .isSuccess("false")
                        .build());
    }
}