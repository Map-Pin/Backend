package com.server.mappin.service.impl;

import antlr.Token;
import com.server.mappin.auth.token.TokenProvider;
import com.server.mappin.converter.MemberConverter;
import com.server.mappin.domain.Member;
import com.server.mappin.domain.Shop;
import com.server.mappin.domain.enums.Role;
import com.server.mappin.dto.Login.LoginResponseDto;
import com.server.mappin.dto.Login.MemberLoginDto;
import com.server.mappin.repository.MemberRepository;
import com.server.mappin.repository.ShopRepository;
import com.server.mappin.service.MapService;
import com.server.mappin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final ShopRepository shopRepository;
    private final MapService mapService;
    @Value("${spring.jwt.access-token-validity-in-seconds}") long accessTokenValidityInMilliseconds;


    @Transactional
    @Override
    public LoginResponseDto login(MemberLoginDto memberCreateDto) {
        Member save;
        String jwt;
        Optional<Member> existingMember = memberRepository.findByEmail(memberCreateDto.getEmail());

        if (existingMember.isPresent()) {
            save = existingMember.get();
        } else {
            Member member = MemberConverter.toMember(memberCreateDto);
            save = memberRepository.save(member);
        }
        jwt = tokenProvider.generateToken(save.getEmail(), save.getRole().toString());
        if(save.getRole() == Role.OWNER){
            Optional<Shop> byMember = shopRepository.findByMember(save);
            if(byMember.isPresent()){
                Shop shop = byMember.get();
                Point point = mapService.GetLocalInfo(shop.getAddress());

                return MemberConverter.toAdminLogin(save,jwt,point,accessTokenValidityInMilliseconds);
            }
        }
        return MemberConverter.toUserLogin(save,jwt,accessTokenValidityInMilliseconds);

    }
}
