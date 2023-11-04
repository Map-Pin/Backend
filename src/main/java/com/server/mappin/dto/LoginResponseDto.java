package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String isSuccess;
    private Long id;
    private String jwt;
    private String token_type;
    private long expires_in;
}
