package com.server.mappin.controller;

import com.server.mappin.dto.LoginResponseDto;
import com.server.mappin.dto.MemberLoginDto;
import com.server.mappin.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Member API", description = "Member 관련 API 명세서")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로그인", description = "새로운 회원은 회원가입, 기존 회원은 로그인")
    @ApiResponses({
            @ApiResponse(content = @Content(schema = @Schema(implementation = LoginResponseDto.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<?> create(@RequestBody MemberLoginDto memberCreateDto){
        try{
            LoginResponseDto loginResponseDto = memberService.create(memberCreateDto);
            return new ResponseEntity<>(loginResponseDto,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
        }
    }


}
