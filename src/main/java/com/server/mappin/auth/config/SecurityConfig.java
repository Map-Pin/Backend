package com.server.mappin.auth.config;

import com.server.mappin.auth.handler.MyAuthenticationFailureHandler;
import com.server.mappin.auth.handler.MyAuthenticationSuccessHandler;
import com.server.mappin.auth.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**","/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login() //Oauth2 로그인 설정 시작점
                .userInfoEndpoint()//Oauth2 로그인 성공 이후 설정 담당
                .userService(customOAuth2UserService)//Oauth2 로그인 성공 시, 후작업을 진행할 CustomOauth2UserService
                .and()
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler);
        return http.build();
    }
}
