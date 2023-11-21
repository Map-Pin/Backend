package com.server.mappin.converter;

import com.server.mappin.domain.Category;
import com.server.mappin.domain.Location;
import com.server.mappin.domain.Lost;
import com.server.mappin.domain.Member;
import com.server.mappin.dto.Lost.LostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LostConverter {



    public static LostDTO.LostRegisterResponseDto toLostRegister(Lost lost){
        return LostDTO.LostRegisterResponseDto.builder()
                .title(lost.getTitle())
                .content(lost.getContent())
                .x(lost.getX())
                .y(lost.getY())
                .foundDate(lost.getFoundDate())
                .createdAt(lost.getCreatedAt())
                .image(lost.getImageUrl())
                .category(lost.getCategory().getName())
                .memberId(lost.getMember().getId())
                .dong(lost.getLocation().getDong())
                .lostId(lost.getId())
                .build();
    }

    public static Lost toLost(
            LostDTO.LostRegisterRequestDto lostRegisterRequestDto,
            LocalDate localDate,
            String imageUrl,
            Category category,
            Location location,
            Member member){
        return Lost.builder()
                .title(lostRegisterRequestDto.getTitle())
                .content(lostRegisterRequestDto.getContent())
                .x(lostRegisterRequestDto.getX())
                .y(lostRegisterRequestDto.getY())
                .foundDate(localDate)
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .category(category)
                .location(location)
                .member(member)
                .build();
    }

    public static LostDTO.LostUpdateResponseDto toLostUpdate(Lost lost){
        return LostDTO.LostUpdateResponseDto.builder()
                .lostId(lost.getId())
                .memberId(lost.getMember().getId())
                .title(lost.getTitle())
                .image(lost.getImageUrl())
                .createdAt(lost.getCreatedAt())
                .content(lost.getContent())
                .x(lost.getX())
                .y(lost.getY())
                .dong(lost.getLocation().getDong())
                .category(lost.getCategory().getName())
                .foundDate(lost.getFoundDate())
                .build();
    }

}
