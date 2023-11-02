package com.server.mappin.service;

import com.server.mappin.auth.token.TokenProvider;
import com.server.mappin.domain.Member;
import com.server.mappin.domain.enums.ProviderType;
import com.server.mappin.dto.LoginResponseDto;
import com.server.mappin.dto.MemberLoginDto;
import com.server.mappin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        Member save;

        Optional<Member> existingMember = memberRepository.findByEmail(memberCreateDto.getEmail());

        if (existingMember.isPresent()) {
            save = existingMember.get();
        } else {
            Member member = new Member();
            member.setEmail(memberCreateDto.getEmail());
            member.setRole(memberCreateDto.getRole());
            member.setName(memberCreateDto.getName());
            member.setCreatedAt(LocalDate.now());
            member.setProviderType(ProviderType.KAKAO);

            save = memberRepository.save(member);
        }

        String jwt = tokenProvider.generateToken(save.getEmail(), save.getRole().toString());

        return LoginResponseDto.builder()
                .id(save.getId())
                .jwt(jwt)
                .build();
    }
}
