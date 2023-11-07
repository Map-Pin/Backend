package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
public class LostSearchByIdResponseDto {
    private int statusCode;
    private String isSuccess;
    private String title;
    private String content;
    private LocalDate foundDate;
    private LocalDateTime createdAt;
    private String image;
    private Double x;
    private Double y;
    private String dong;
    private String category;
}
