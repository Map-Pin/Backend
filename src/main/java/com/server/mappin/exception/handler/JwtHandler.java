package com.server.mappin.exception.handler;

import com.server.mappin.common.BaseErrorCode;
import com.server.mappin.common.status.ErrorStatus;
import org.springframework.security.core.AuthenticationException;

public class JwtHandler extends AuthenticationException {
    public JwtHandler(ErrorStatus status){
        super(status.name());
    }
}
