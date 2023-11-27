package com.server.mappin.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.mappin.common.ErrorReasonDto;
import com.server.mappin.common.status.ErrorStatus;
import com.server.mappin.exception.handler.JwtHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{
        try{
            filterChain.doFilter(request,response);
        }catch (JwtHandler handler){

            response.setContentType("application/json; charset=UTF-8");

            String errorName = handler.getMessage();
            ErrorStatus errorStatus = ErrorStatus.valueOf(errorName);

            ErrorReasonDto errorReasonDto = ErrorReasonDto.builder()
                    .httpStatus(errorStatus.getHttpStatus())
                    .code(errorStatus.getCode())
                    .message(errorStatus.getMessage())
                    .success(false)
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(errorReasonDto);
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

}
