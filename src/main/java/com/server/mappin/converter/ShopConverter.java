package com.server.mappin.converter;

import com.server.mappin.domain.Location;
import com.server.mappin.domain.Lost;
import com.server.mappin.domain.Member;
import com.server.mappin.domain.Shop;
import com.server.mappin.dto.Shop.ShopDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShopConverter {

    public static ShopDTO.ShopListRP toShopListResponse(List<Lost> losts){
        return ShopDTO.ShopListRP.builder()
                .result(losts.stream().map(shop -> ShopDTO.ShopRP.builder()
                                .id(shop.getId())
                                .title(shop.getTitle())
                                .shopName(shop.getTitle())
                                .createdAt(shop.getCreatedAt())
                                .imageUrl(shop.getImageUrl())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public static Shop toShop(Member member, ShopDTO.ShopRegisterRQ shopRegisterRQ, Location location){
        return Shop.builder()
                .member(member)
                .name(shopRegisterRQ.getName())
                .address(shopRegisterRQ.getAddress())
                .location(location)
                .point(50)
                .companyNumber(shopRegisterRQ.getCompanyNumber())
                .build();
    }

    public static ShopDTO.ShopRegisterRP toShopRegisterResponse(Shop shop, String dong){
        return ShopDTO.ShopRegisterRP.builder()
                .id(shop.getId())
                .memberId(shop.getMember().getId())
                .name(shop.getName())
                .address(shop.getAddress())
                .dong(dong)
                .companyNumber(shop.getCompanyNumber())
                .build();
    }
}
