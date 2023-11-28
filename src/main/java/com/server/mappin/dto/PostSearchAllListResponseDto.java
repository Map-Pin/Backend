package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PostSearchAllListResponseDto {
    private int statusCode;
    private String isSuccess;
    private List<PostSearchAllResponseDto> posts;
}
