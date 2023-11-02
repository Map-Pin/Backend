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
        Optional<Member> member1 = memberRepository.findByEmail(memberCreateDto.getEmail());
        if(member1.isEmpty()){
            Member member = new Member();
            member.builder()
                    .email(memberCreateDto.getEmail())
                    .role(memberCreateDto.getRole())
                    .name(memberCreateDto.getName())
                    .createdAt(LocalDateTime.now())
                    .providerType(ProviderType.KAKAO)
                    .build();

            save = memberRepository.save(member);
        } else {
            save = member1.get();
        }
        String jwt = tokenProvider.generateToken(memberCreateDto.getEmail(), memberCreateDto.getRole().toString());

        return LoginResponseDto.builder()
                .id(save.getId())
                .jwt(jwt)
                .build();
    }
}
