package com.server.mappin.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.mappin.common.ErrorReasonDto;
import com.server.mappin.common.status.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authExeption)
        throws IOException, ServletException{
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ErrorReasonDto errorReasonDto = ErrorReasonDto.builder()
                .httpStatus(ErrorStatus._UNAUTHORIZED.getHttpStatus())
                .code(ErrorStatus._UNAUTHORIZED.getCode())
                .message(ErrorStatus._UNAUTHORIZED.getMessage())
                .success(false)
                .build();

        log.error("Unauthorized error");
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(errorReasonDto);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

}
