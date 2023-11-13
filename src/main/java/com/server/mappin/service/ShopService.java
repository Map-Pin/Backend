package com.server.mappin.service;

import com.server.mappin.domain.Location;
import com.server.mappin.domain.Member;
import com.server.mappin.domain.Shop;
import com.server.mappin.domain.enums.Role;
import com.server.mappin.dto.Shop.ShopDTO;
import com.server.mappin.repository.LocationRepository;
import com.server.mappin.repository.MemberRepository;
import com.server.mappin.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  public ShopDTO.ShopRegisterResponseDto shopRegister(ShopDTO.ShopRegisterRequestDto shopRegisterRequestDto, String email) {
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
      return ShopDTO.ShopRegisterResponseDto.builder()
                .id(save.getId())
                .memberId(save1.getId())
                .name(save.getName())
                .address(save.getAddress())
                .dong(dong)
                .companyNumber(save.getCompanyNumber())
                .build();
    }
    return ShopDTO.ShopRegisterResponseDto.builder()
            .build();
  }
}
