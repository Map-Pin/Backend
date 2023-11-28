package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
public class PostSearchAllResponseDto {
    private Long id;
    private String title;
    private String createdAt;
    private String image;
    private String dong;
}
