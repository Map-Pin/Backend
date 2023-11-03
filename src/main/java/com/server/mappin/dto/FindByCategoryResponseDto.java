package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindByCategoryResponseDto {
    private Long id;
    private String title;
}
