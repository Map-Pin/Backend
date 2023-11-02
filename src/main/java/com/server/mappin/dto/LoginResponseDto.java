package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private Long id;
    private String jwt;
}
