package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreateResponseDto {
    private int statusCode;
    private String isSuccess;
    private Long postId;

}
