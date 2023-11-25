package com.server.mappin.service;

import com.server.mappin.auth.token.TokenProvider;
import com.server.mappin.converter.MemberConverter;
import com.server.mappin.domain.Member;
import com.server.mappin.domain.Shop;
import com.server.mappin.domain.enums.ProviderType;
import com.server.mappin.domain.enums.Role;
import com.server.mappin.dto.Login.AdminLoginResponseDto;
import com.server.mappin.dto.Login.LoginResponseDto;
import com.server.mappin.dto.Login.MemberLoginDto;
import com.server.mappin.dto.Login.UserLoginResponseDto;
import com.server.mappin.repository.MemberRepository;
import com.server.mappin.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;


public interface MemberService {
    LoginResponseDto login(MemberLoginDto memberCreateDto);
}
