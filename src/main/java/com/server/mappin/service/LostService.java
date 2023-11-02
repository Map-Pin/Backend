package com.server.mappin.service;

import com.server.mappin.auth.token.TokenProvider;
import com.server.mappin.domain.Member;
import com.server.mappin.dto.LoginResponseDto;
import com.server.mappin.dto.MemberLoginDto;
import com.server.mappin.repository.LostRepository;
import com.server.mappin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LostService {
    private final LostRepository lostRepository;

    @Transactional
    public void save(){

    }
}
