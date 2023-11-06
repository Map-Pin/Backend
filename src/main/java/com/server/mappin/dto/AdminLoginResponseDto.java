package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.geo.Point;

@Data
@Builder
@Getter
public class AdminLoginResponseDto implements LoginResponseDto {
    private int statusCode;
    private String isSuccess;
    private Long id;
    private String role;
    private String jwt;
    private String token_type;
    private long expires_in;
    private Point geo;

    @Override
    public int getStatusCode(){
        return statusCode;
    }
    @Override
    public String getIsSuccess() {
        return isSuccess;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getJwt() {
        return jwt;
    }

    @Override
    public String getToken_Type() {
        return token_type;
    }

    @Override
    public long getExpires_In() {
        return expires_in;
    }

}
