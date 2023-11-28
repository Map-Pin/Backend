package com.server.mappin.dto;

public interface LoginResponseDto {
    int getStatusCode();
    String getIsSuccess();
    Long getId();
    String getRole();
    String getJwt();
    String getToken_Type();
    long getExpires_In();
}
