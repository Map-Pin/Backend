package com.server.mappin.controller;

import com.server.mappin.dto.FindByCategoryResponseDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import java.util.List;
@Tag(name = "Lost API", description = "분실물 관련 API 명세서")
@RestController
@Slf4j
@RequiredArgsConstructor
public class LostController {
    private final LostService lostService;

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
}
