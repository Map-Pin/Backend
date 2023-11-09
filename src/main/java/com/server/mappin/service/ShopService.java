package com.server.mappin.service;

import com.server.mappin.domain.Location;
import com.server.mappin.domain.Member;
import com.server.mappin.domain.Shop;
import com.server.mappin.domain.enums.Role;
import com.server.mappin.dto.ShopRegisterRequestDto;
import com.server.mappin.dto.ShopRegisterResponseDto;
import com.server.mappin.repository.LocationRepository;
import com.server.mappin.repository.MemberRepository;
import com.server.mappin.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {
  private final ShopRepository shopRepository;
  private final MemberRepository memberRepository;
  private final LocationRepository locationRepository;
  private final MapService mapService;

  @Transactional
  public ShopRegisterResponseDto shopRegister(ShopRegisterRequestDto shopRegisterRequestDto, String email) {
    Optional<Member> memberByEmail = memberRepository.findByEmail(email);
    Point point = mapService.GetLocalInfo(shopRegisterRequestDto.getAddress());
    String dong = mapService.getDong(point.getX(), point.getY());
    Optional<Location> locationByDong = locationRepository.findLocationByDong(dong);

    if (memberByEmail.isPresent() && locationByDong.isPresent()) {
      Member member = memberByEmail.get();
      member.setRole(Role.OWNER);
      Shop shop = Shop.builder()
              .member(member)
              .name(shopRegisterRequestDto.getName())
              .address(shopRegisterRequestDto.getAddress())
              .location(locationByDong.get())
              .point(50)
              .companyNumber(shopRegisterRequestDto.getCompanyNumber())
              .build();
      Member save1 = memberRepository.save(member);
      Shop save = shopRepository.save(shop);
      return ShopRegisterResponseDto.builder()
                .shopId(save.getId())
                .memberId(save1.getId())
                .name(save.getName())
                .address(save.getAddress())
                .dong(dong)
                .companyNumber(save.getCompanyNumber())
                .isSuccess("true")
                .statusCode(200)
                .build();
    }
    return ShopRegisterResponseDto.builder()
            .isSuccess("false")
            .statusCode(400)
            .build();
  }
}
