package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class FindByCategoryResponseDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private String imageUrl;
    private String dong;
}
