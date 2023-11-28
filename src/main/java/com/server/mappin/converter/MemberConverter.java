package com.server.mappin.converter;


import com.server.mappin.domain.Member;
import com.server.mappin.domain.enums.ProviderType;
import com.server.mappin.domain.enums.Role;
import com.server.mappin.dto.Login.AdminLoginResponseDto;
import com.server.mappin.dto.Login.MemberLoginDto;
import com.server.mappin.dto.Login.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MemberConverter {

    public static AdminLoginResponseDto toAdminLogin(Member member, String jwt, Point point, long expires ){
        return AdminLoginResponseDto.builder()
                .id(member.getId())
                .jwt(jwt)
                .token_type("Bearer")
                .expires_in(expires)
                .role(member.getRole().name())
                .geo(point)
                .build();
    }

    public static UserLoginResponseDto toUserLogin(Member member,String jwt, long expires){
        return UserLoginResponseDto.builder()
                .id(member.getId())
                .role(member.getRole().name())
                .jwt(jwt)
                .token_type("Bearer")
                .expires_in(expires)
                .build();
    }

    public static Member toMember(MemberLoginDto memberLoginDto){
        return Member.builder()
                .email(memberLoginDto.getEmail())
                .role(Role.USER)
                .name(memberLoginDto.getName())
                .createdAt(LocalDate.now())
                .providerType(ProviderType.KAKAO)
                .build();
    }

}
