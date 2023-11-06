package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class PostCreateResponseDto {
    private int statusCode;
    private String isSuccess;
    private Long memberId;
    private Long postId;
    private String title;
    private String content;
    private LocalDate lostDate;
    private LocalDateTime createdAt;
    private String image;
    private Double x;
    private Double y;
    private String dong;
    private String category;
}

