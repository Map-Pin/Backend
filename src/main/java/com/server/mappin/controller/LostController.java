package com.server.mappin.controller;

import com.server.mappin.dto.FindByCategoryResponseDto;
import com.server.mappin.dto.FindByDongResponseDto;
import com.server.mappin.dto.LostRegisterRequestDto;
import com.server.mappin.dto.LostRegisterResponseDto;
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
import java.util.List;
@Tag(name = "Lost API", description = "분실물 관련 API 명세서")
@RestController
@Slf4j
@RequiredArgsConstructor
public class LostController {
    private final LostService lostService;

    @Operation(summary = "분실물 등록")
    @ApiResponse(content = @Content(schema = @Schema(implementation = LostRegisterResponseDto.class)))
    @PutMapping("/lost/register")
    public ResponseEntity<?> registerLost(@RequestBody LostRegisterRequestDto lostRegisterRequestDto, Authentication authentication){
        try{
            LostRegisterResponseDto lostRegisterResponseDto = lostService.registerLost(lostRegisterRequestDto, authentication.getName());
            return new ResponseEntity<>(lostRegisterResponseDto,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다",HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "카테고리 검색",description = "분실물을 카테고리 별로 검색")
    @ApiResponse(content = @Content(schema = @Schema(implementation = FindByCategoryResponseDto.class)))
    @GetMapping("/search/category/{category_name}")
    public ResponseEntity<?> searchByCategory(@RequestParam(value = "category_name") String name){
        try{
            List<FindByCategoryResponseDto> byCategory = lostService.findByCategory(name);
            return new ResponseEntity<>(byCategory,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "동 검색",description = "분실물을 동 별로 검색")
    @ApiResponse(content = @Content(schema = @Schema(implementation = FindByDongResponseDto.class)))
    @GetMapping("/search/dong/{dong_name}")
    public ResponseEntity<?> searchByDong(@RequestParam(value = "dong_name") String dongName){
        try{
            List<FindByDongResponseDto> byDong = lostService.findByDong(dongName);
            return new ResponseEntity<>(byDong,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
        }
    }
}
