package com.server.mappin.service;

import com.server.mappin.domain.Category;
import com.server.mappin.domain.Location;
import com.server.mappin.domain.Lost;
import com.server.mappin.domain.Member;
import com.server.mappin.dto.FindByCategoryResponseDto;
import com.server.mappin.dto.LostRegisterRequestDto;
import com.server.mappin.dto.LostRegisterResponseDto;
import com.server.mappin.repository.CategoryRepository;
import com.server.mappin.repository.LocationRepository;
import com.server.mappin.repository.LostRepository;
import com.server.mappin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LostService {
    private final LostRepository lostRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    public List<FindByCategoryResponseDto> findByCategory(String categoryName){
        List<Lost> losts = lostRepository.findByCategory(categoryName);
        List<FindByCategoryResponseDto> result = losts.stream()
                .map(lost -> FindByCategoryResponseDto.builder()
                        .id(lost.getId())
                        .title(lost.getTitle())
                        .build())
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public LostRegisterResponseDto registerLost(LostRegisterRequestDto lostRegisterRequestDto, String email){
        Optional<Member> memberRepositoryByEmail = memberRepository.findByEmail(email);
        Optional<Category> categoryByName = categoryRepository.findCategoryByName(lostRegisterRequestDto.getCategory());
        Optional<Location> locationByDong = locationRepository.findLocationByDong(lostRegisterRequestDto.getDong());
        if(memberRepositoryByEmail.isPresent() && categoryByName.isPresent() && locationByDong.isPresent()){
            Member member = memberRepositoryByEmail.get();
            Location location = locationByDong.get();
            Category category = categoryByName.get();
            Lost lost = Lost.builder()
                    .title(lostRegisterRequestDto.getTitle())
                    .content(lostRegisterRequestDto.getContent())
                    .x(lostRegisterRequestDto.getX())
                    .y(lostRegisterRequestDto.getY())
                    .foundDate(lostRegisterRequestDto.getFoundDate())
                    .createdAt(LocalDateTime.now())
                    .category(category)
                    .location(location)
                    .member(member)
                    .build();
            Lost save = lostRepository.save(lost);
            return LostRegisterResponseDto.builder()
                    .isSuccess("true")
                    .lostId(save.getId())
                    .build();
        }
        return LostRegisterResponseDto.builder()
                .isSuccess("false")
                .build();

    }
}
