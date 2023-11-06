package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LostRegisterResponseDto {
    private int statusCode;
    private String isSuccess;
    private Long lostId;
}
