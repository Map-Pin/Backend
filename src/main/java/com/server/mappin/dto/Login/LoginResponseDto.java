package com.server.mappin.dto.Login;

public interface LoginResponseDto {
    Long getId();
    String getRole();
    String getJwt();
    String getToken_Type();
    long getExpires_In();
}
