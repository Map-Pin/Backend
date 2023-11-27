package com.server.mappin.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.mappin.common.ErrorReasonDto;
import com.server.mappin.common.status.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        ErrorReasonDto errorReasonDto = ErrorReasonDto.builder()
                .httpStatus(ErrorStatus._FORBIDDEN.getHttpStatus())
                .code(ErrorStatus._FORBIDDEN.getCode())
                .message(ErrorStatus._FORBIDDEN.getMessage())
                .success(false)
                .build();

        log.error("Access Denied error");
        //response.sendError(HttpServletResponse.SC_FORBIDDEN);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(errorReasonDto);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

}
