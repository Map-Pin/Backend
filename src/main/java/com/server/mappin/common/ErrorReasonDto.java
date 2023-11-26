package com.server.mappin.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReasonDto {
    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;

}
