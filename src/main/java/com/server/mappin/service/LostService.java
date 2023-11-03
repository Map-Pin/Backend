package com.server.mappin.service;

import com.server.mappin.domain.Lost;
import com.server.mappin.dto.FindByCategoryResponseDto;
import com.server.mappin.repository.LostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LostService {
    private final LostRepository lostRepository;
    @Transactional
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
}
