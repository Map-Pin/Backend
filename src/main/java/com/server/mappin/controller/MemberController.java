package com.server.mappin.controller;

import com.server.mappin.common.BaseResponseDto;
import com.server.mappin.common.status.SuccessStatus;
import com.server.mappin.dto.Login.AdminLoginResponseDto;
import com.server.mappin.dto.Login.LoginResponseDto;
import com.server.mappin.dto.Login.MemberLoginDto;
import com.server.mappin.dto.Login.UserLoginResponseDto;
import com.server.mappin.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Member API", description = "Member 관련 API 명세서")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로그인", description = "새로운 회원은 회원가입, 기존 회원은 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",description ="일반 사용자 로그인"),
    })
    @PostMapping("/login")
    public BaseResponseDto<LoginResponseDto> login(@RequestBody MemberLoginDto memberCreateDto){
            LoginResponseDto responseDto = memberService.login(memberCreateDto);
            return BaseResponseDto.onSuccess(responseDto);
    }


}
