package com.server.mappin.auth.token;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}