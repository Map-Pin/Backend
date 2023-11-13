package com.server.mappin.service;

import com.server.mappin.domain.*;
import com.server.mappin.dto.Category.CategoryDTO;
import com.server.mappin.dto.Location.LocationDTO;
import com.server.mappin.dto.Lost.*;
import com.server.mappin.dto.Shop.ShopDTO;
import com.server.mappin.repository.*;
import lombok.RequiredArgsConstructor;
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

  public CategoryDTO.CategoryListResponseDTO findByCategory(String categoryName) {
    List<Lost> losts = lostRepository.findByCategory(categoryName);

    return CategoryDTO.CategoryListResponseDTO.builder()
            .result(losts.stream().map(lost -> CategoryDTO.CategoryResponseDTO.builder()
                    .id(lost.getId())
                    .title(lost.getTitle())
                    .creatdAt(lost.getCreatedAt())
                    .imageUrl(lost.getImageUrl())
                    .build())
                    .collect(Collectors.toList()))
            .build();
  }

  public LocationDTO.LocationListResponseDTO findByDong(String dongName) {
    List<Lost> dongs = lostRepository.findLocationByDong(dongName);
    return LocationDTO.LocationListResponseDTO.builder()
            .result(dongs.stream().map(lost -> LocationDTO.LocationResponseDTO.builder()
                            .id(lost.getId())
                            .title(lost.getTitle())
                            .createdAt(lost.getCreatedAt())
                            .imageUrl(lost.getImageUrl())
                            .build())
                    .collect(Collectors.toList()))
            .build();
  }

  public ShopDTO.ShopListResponseDTO findByShop(String shopName) {
    List<Lost> shops = lostRepository.findLostByShopName(shopName);


    return ShopDTO.ShopListResponseDTO.builder()
            .result(shops.stream().map(shop -> ShopDTO.ShopResponseDTO.builder()
                            .id(shop.getId())
                            .title(shop.getTitle())
                            .shopName(shopName)
                            .createdAt(shop.getCreatedAt())
                            .imageUrl(shop.getX() + " " + shop.getY())
                            .build())
                    .collect(Collectors.toList()))
            .build();
  }

  /*public FindByDongListResponseDto findByCurrentLocation(Double x, Double y) {
    String dong = mapService.getDong(x, y);
    Optional<Location> locationByDong = locationRepository.findLocationByDong(dong);
    if (locationByDong.isPresent()) {
      Location location = locationByDong.get();
      return findByDong(location.getDong());
    }
    return null;
  }
  */

  public LocationDTO.LocationListResponseDTO findByCurrentLocation(Double x, Double y) {
    List<Lost> locationByDong = lostRepository.findAll();
    return LocationDTO.LocationListResponseDTO.builder()
            .result(locationByDong.stream().map(lost -> LocationDTO.LocationResponseDTO.builder()
                            .id(lost.getId())
                            .title(lost.getTitle())
                            .createdAt(lost.getCreatedAt())
                            .imageUrl(lost.getImageUrl())
                            .build())
                    .collect(Collectors.toList()))
            .build();
  }



    @Transactional
    public LostDTO.LostRegisterResponseDto registerLost(LostDTO.LostRegisterRequestDto lostRegisterRequestDto, MultipartFile image, String email) throws IOException {
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
            return LostDTO.LostRegisterResponseDto.builder()
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
        return LostDTO.LostRegisterResponseDto.builder()
                .build();

    }

    public LostDTO.LostSearchByIdResponseDto getById(Long id){
        Optional<Lost> lostById = lostRepository.findById(id);
        return lostById
                .map(lost -> LostDTO.LostSearchByIdResponseDto.builder()
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
                .orElse(LostDTO.LostSearchByIdResponseDto.builder()
                        .build());
    }
    @Transactional
  public LostDTO.LostUpdateResponseDto update(Long lostId, LostDTO.LostUpdateRequestDto lostUpdateRequestDto, MultipartFile image, String email) throws IOException {
    Lost lost = lostRepository.findById(lostId).orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다"));
    Optional<Member> member = memberRepository.findByEmail(email);
    if(member.isEmpty()){
      return LostDTO.LostUpdateResponseDto.builder().build();
    }
    System.out.println("member = " + member.get().getName());

    // 업데이트 필드를 확인하고 필요한 경우 업데이트
    if (lostUpdateRequestDto.getFoundDate() != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate localDate = LocalDate.parse(lostUpdateRequestDto.getFoundDate(), formatter);
      lost.setFoundDate(localDate);
    }

    if (lostUpdateRequestDto.getX() != null && lostUpdateRequestDto.getY() != null) {
      String dong = mapService.getDong(lostUpdateRequestDto.getX(), lostUpdateRequestDto.getY());
      Optional<Location> locationByDong = locationRepository.findLocationByDong(dong);
      locationByDong.ifPresent(lost::setLocation);
    }

    if (lostUpdateRequestDto.getCategory() != null) {
      Optional<Category> categoryByName = categoryRepository.findCategoryByName(lostUpdateRequestDto.getCategory());
      categoryByName.ifPresent(lost::setCategory);
    }

    if (lostUpdateRequestDto.getContent() != null) {
      lost.setContent(lostUpdateRequestDto.getContent());
    }

    if (lostUpdateRequestDto.getTitle() != null) {
      System.out.println("LOST = " + lostUpdateRequestDto.getTitle());
      lost.setTitle(lostUpdateRequestDto.getTitle());
    }

    if (image != null) {
      // 이미지 업로드 및 기존 이미지 삭제 처리
      if (!s3Service.findByUrl(lost.getImageUrl()).isEmpty()) {
        s3Service.delete(lost.getImageUrl());
      }
      String imageUrl = s3Service.upload(image, "images");
      lost.setImageUrl(imageUrl);
    }

    System.out.println("lost title: " + lost.getTitle());
    // 게시물 업데이트
    Lost updatedLost = lostRepository.save(lost);

    if (updatedLost != null) {
      return LostDTO.LostUpdateResponseDto.builder()
              .lostId(updatedLost.getId())
              .memberId(updatedLost.getMember().getId())
              .title(updatedLost.getTitle())
              .image(updatedLost.getImageUrl())
              .createdAt(updatedLost.getCreatedAt())
              .content(updatedLost.getContent())
              .x(updatedLost.getX())
              .y(updatedLost.getY())
              .dong(updatedLost.getLocation().getDong())
              .category(updatedLost.getCategory().getName())
              .foundDate(updatedLost.getFoundDate())
              .build();
    } else {
      return LostDTO.LostUpdateResponseDto.builder()
              .build();
    }
  }
}