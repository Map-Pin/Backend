package com.server.mappin.service.impl;

import com.server.mappin.converter.ShopConverter;
import com.server.mappin.domain.Location;
import com.server.mappin.domain.Member;
import com.server.mappin.domain.Shop;
import com.server.mappin.domain.enums.Role;
import com.server.mappin.dto.Shop.ShopDTO;
import com.server.mappin.repository.LocationRepository;
import com.server.mappin.repository.MemberRepository;
import com.server.mappin.repository.ShopRepository;
import com.server.mappin.service.MapService;
import com.server.mappin.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    private final MapService mapService;

    @Transactional
    @Override
    public ShopDTO.ShopRegisterRP shopRegister(ShopDTO.ShopRegisterRQ shopRegisterRQ, String email) {
        Optional<Member> memberByEmail = memberRepository.findByEmail(email);
        Point point = mapService.GetLocalInfo(shopRegisterRQ.getAddress());
        String dong = mapService.getDong(point.getX(), point.getY());
        Optional<Location> locationByDong = locationRepository.findLocationByDong(dong);

        if (memberByEmail.isPresent() && locationByDong.isPresent()) {
            Member member = memberByEmail.get();
            member.setRole(Role.OWNER);
            Shop shop = Shop.builder()
                    .member(member)
                    .name(shopRegisterRQ.getName())
                    .address(shopRegisterRQ.getAddress())
                    .location(locationByDong.get())
                    .point(50)
                    .companyNumber(shopRegisterRQ.getCompanyNumber())
                    .build();
            Member save1 = memberRepository.save(member);
            Shop save = shopRepository.save(shop);
            return ShopConverter.toShopRegisterResponse(save,dong);
        }
        return ShopDTO.ShopRegisterRP.builder()
                .build();
    }
}
