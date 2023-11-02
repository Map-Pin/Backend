package com.server.mappin.service;

import com.server.mappin.auth.token.TokenProvider;
import com.server.mappin.domain.Member;
import com.server.mappin.dto.LoginResponseDto;
import com.server.mappin.dto.MemberLoginDto;
import com.server.mappin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    @Transactional
    public LoginResponseDto create(MemberLoginDto memberCreateDto) {
        Member member = new Member();
        member.setEmail(memberCreateDto.getEmail());
        member.setRole(memberCreateDto.getRole());
        String jwt = tokenProvider.generateToken(memberCreateDto.getEmail(), memberCreateDto.getRole().toString());
        Member save = memberRepository.save(member);

        return LoginResponseDto.builder()
                .id(save.getId())
                .jwt(jwt)
                .build();
    }
}
