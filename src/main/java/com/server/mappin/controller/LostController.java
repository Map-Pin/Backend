package com.server.mappin.controller;

import com.server.mappin.dto.*;
import com.server.mappin.service.LostService;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.io.IOException;
import java.util.List;
@Tag(name = "Lost API", description = "분실물 관련 API 명세서")
@RestController
@Slf4j
@RequiredArgsConstructor
public class LostController {
    private final LostService lostService;

    @Operation(summary = "분실물 등록")
    @ApiResponse(responseCode ="200",description ="분실물 등록 성공",content = @Content(schema = @Schema(implementation = LostRegisterResponseDto.class)))
    @ApiResponse(responseCode ="400",description ="분실물 등록 실패",content = @Content(schema = @Schema(implementation = LostRegisterResponseDto.class)))
    @PutMapping("/lost/register")
    public ResponseEntity<?> registerLost(@ModelAttribute LostRegisterRequestDto lostRegisterRequestDto, Authentication authentication) throws IOException {
        try{
            LostRegisterResponseDto lostRegisterResponseDto = lostService.registerLost(lostRegisterRequestDto, authentication.getName());
            return new ResponseEntity<>(lostRegisterResponseDto,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다",HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "카테고리 검색",description = "분실물을 카테고리 별로 검색")
    @ApiResponse(responseCode = "200",description ="카테고리 별로 검색 성공", content = @Content(schema = @Schema(implementation = FindByCategoryListResponseDto.class)))
    @GetMapping("/search/category/{category_name}")
    public ResponseEntity<?> searchByCategory(@RequestParam(value = "category_name") String name){
        try{
            FindByCategoryListResponseDto findByCategoryListResponseDto = lostService.findByCategory(name);
            return new ResponseEntity<>(findByCategoryListResponseDto,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
        }
    }
}
