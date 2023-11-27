package com.server.mappin.auth.filter;

import com.server.mappin.auth.config.SecurityConfig;
import com.server.mappin.auth.token.TokenProvider;
import com.server.mappin.common.status.ErrorStatus;
import com.server.mappin.exception.handler.JwtHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{
        String token = tokenProvider.resolveToken(request);

        if(request.getRequestURI().startsWith("/swagger-ui/") || request.getRequestURI().startsWith("/v3/") || request.getRequestURI().startsWith("/login")){
            filterChain.doFilter(request,response);
            return;
        }
        if(StringUtils.hasText(token) && tokenProvider.validateToken(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            throw new JwtHandler(ErrorStatus.JWT_TOKEN_NOT_FOUND);
        }
        filterChain.doFilter(request,response);

    }
}
