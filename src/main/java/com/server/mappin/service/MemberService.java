package com.server.mappin.service;

import com.server.mappin.auth.token.TokenProvider;
import com.server.mappin.domain.Member;
import com.server.mappin.domain.Shop;
import com.server.mappin.domain.enums.ProviderType;
import com.server.mappin.domain.enums.Role;
import com.server.mappin.dto.AdminLoginResponseDto;
import com.server.mappin.dto.LoginResponseDto;
import com.server.mappin.dto.UserLoginResponseDto;
import com.server.mappin.dto.MemberLoginDto;
import com.server.mappin.repository.MemberRepository;
import com.server.mappin.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final ShopRepository shopRepository;
    private final MapService mapService;
    @Value("${spring.jwt.access-token-validity-in-seconds}") long accessTokenValidityInMilliseconds;

    @Transactional
    public LoginResponseDto login(MemberLoginDto memberCreateDto) {
        Member save;
        String jwt;
        Optional<Member> existingMember = memberRepository.findByEmail(memberCreateDto.getEmail());

        if (existingMember.isPresent()) {
            save = existingMember.get();
        } else {
            Member member = new Member();
            member.setEmail(memberCreateDto.getEmail());
            member.setRole(Role.USER);
            member.setName(memberCreateDto.getName());
            member.setCreatedAt(LocalDate.now());
            member.setProviderType(ProviderType.KAKAO);
            save = memberRepository.save(member);
        }
        jwt = tokenProvider.generateToken(save.getEmail(), save.getRole().toString());
        if(save.getRole() == Role.OWNER){
            Optional<Shop> byMember = shopRepository.findByMember(save);
            if(byMember.isPresent()){
                Shop shop = byMember.get();
                Point point = mapService.GetLocalInfo(shop.getAddress());
                return AdminLoginResponseDto.builder()
                        .statusCode(201)
                        .isSuccess("true")
                        .id(save.getId())
                        .jwt(jwt)
                        .token_type("Bearer")
                        .expires_in(accessTokenValidityInMilliseconds)
                        .role(save.getRole().name())
                        .geo(point)
                        .build();
            }
        }
        return UserLoginResponseDto.builder()
                .statusCode(200)
                .isSuccess("true")
                .id(save.getId())
                .role(save.getRole().name())
                .jwt(jwt)
                .token_type("Bearer")
                .expires_in(accessTokenValidityInMilliseconds)
                .build();

    }
}
