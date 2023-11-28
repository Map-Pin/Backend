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

    public static LostDTO.LostRegisterRP toLostRegisterRP(Lost lost){
        return LostDTO.LostRegisterRP.builder()
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
            LostDTO.LostRegisterRQ lostRegisterRQ,
            LocalDate localDate,
            String imageUrl,
            Category category,
            Location location,
            Member member){
        return Lost.builder()
                .title(lostRegisterRQ.getTitle())
                .content(lostRegisterRQ.getContent())
                .x(lostRegisterRQ.getX())
                .y(lostRegisterRQ.getY())
                .foundDate(localDate)
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .category(category)
                .location(location)
                .member(member)
                .build();
    }

    public static LostDTO.LostUpdateRP toLostUpdateRP(Lost lost){
        return LostDTO.LostUpdateRP.builder()
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
