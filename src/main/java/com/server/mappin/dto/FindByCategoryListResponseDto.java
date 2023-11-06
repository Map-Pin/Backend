package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FindByCategoryListResponseDto {
    private int statusCode;
    private String isSuccess;
    private List<FindByCategoryResponseDto> losts;
}
