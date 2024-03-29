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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.io.IOException;
import java.util.List;

@Tag(name = "Lost API", description = "분실물 관련 API 명세서")
@RestController
@Slf4j
@RequiredArgsConstructor
public class LostController {
  private final LostService lostService;

  @Operation(summary = "분실물 등록", description = "Content-Type은 multipart/form-data이지만 info는 application/json입니다")
  @ApiResponse(content = @Content(schema = @Schema(implementation = LostRegisterResponseDto.class)))
  @PutMapping(value = "/lost/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> registerLost(
          @RequestPart("image") MultipartFile file,
          @RequestPart("info") LostRegisterRequestDto lostRegisterRequestDto,
          Authentication authentication) throws IOException {
    try {
      LostRegisterResponseDto lostRegisterResponseDto = lostService.registerLost(lostRegisterRequestDto,file, authentication.getName());
      return new ResponseEntity<>(lostRegisterResponseDto, HttpStatus.OK);
    } catch (IllegalStateException e) {
      return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
    }
  }

  @Operation(summary = "분실물 수정",description = "Content-type은 multipart/form-data이지만 info는 application/json입니다")
  @ApiResponses({
          @ApiResponse(responseCode ="200",description ="분실물 수정 성공",content = @Content(schema = @Schema(implementation = LostUpdateResponseDto.class))),
          @ApiResponse(responseCode ="400",description ="분실물 수정 실패",content = @Content(schema = @Schema(implementation = LostUpdateResponseDto.class)))
  })
  @PatchMapping(value = "/lost/update/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> update(
          @PathVariable("id") Long id,
          @RequestPart("image") MultipartFile image,
          @RequestPart("info") LostUpdateRequestDto lostUpdateRequestDto,
          Authentication authentication
  ) throws IOException {
    try{
      String email = authentication.getName();
      LostUpdateResponseDto lostUpdateResponseDto = lostService.update(id, lostUpdateRequestDto, image, email);
      return new ResponseEntity<>(lostUpdateResponseDto,HttpStatus.OK);
    }catch (IllegalStateException e){
      return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
    }
  }

  @Operation(summary = "카테고리 검색", description = "분실물을 카테고리 별로 검색")
  @ApiResponse(content = @Content(schema = @Schema(implementation = FindByCategoryListResponseDto.class)))
  @GetMapping("lost/search/category")
  public ResponseEntity<?> searchByCategory(@RequestParam(value = "category") String name) {
    try {
      FindByCategoryListResponseDto findByCategoryListResponseDto = lostService.findByCategory(name);
      return new ResponseEntity<>(findByCategoryListResponseDto, HttpStatus.OK);
    } catch (IllegalStateException e) {
      return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
    }
  }

  @Operation(summary = "동 검색", description = "분실물을 동 별로 검색")
  @ApiResponse(content = @Content(schema = @Schema(implementation = FindByDongListResponseDto.class)))
  @GetMapping("lost/search/dong")
  public ResponseEntity<?> searchByDong(@RequestParam(value = "name") String dongName) {
    try {
      FindByDongListResponseDto byDong = lostService.findByDong(dongName);
      return new ResponseEntity<>(byDong, HttpStatus.OK);
    } catch (IllegalStateException e) {
      return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
    }
  }

  @Operation(summary = "가게 검색", description = "분실물을 가게이름 별로 검색")
  @ApiResponse(content = @Content(schema = @Schema(implementation = FindByShopListResponseDto.class)))
  @GetMapping("lost/search/shop")
  public ResponseEntity<?> searchByShop(@RequestParam(value = "name") String shopName) {
    try {
      FindByShopListResponseDto byShop = lostService.findByShop(shopName);
      return new ResponseEntity<>(byShop, HttpStatus.OK);
    } catch (IllegalStateException e) {
      return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
    }
  }

  @Operation(summary = "현재위치로 검색", description = "분실물을 현재위치로 검색")
  @ApiResponse(content = @Content(schema = @Schema(implementation = FindByDongResponseDto.class)))
  @GetMapping("lost/search/location")
  public ResponseEntity<?> searchByCurrentLocation(
          @RequestParam(value = "x") Double x,
          @RequestParam(value = "y") Double y
  ) {
    try {
      FindByDongListResponseDto byDong = lostService.findByCurrentLocation(x, y);
      return new ResponseEntity<>(byDong, HttpStatus.OK);
    } catch (IllegalStateException e) {
      return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
    }
  }

  @Operation(summary = "분실물 상세 조회", description = "분실물 Id로 분실물 상세 조회")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "분실물 상세 조회 성공", content = @Content(schema = @Schema(implementation = LostSearchByIdResponseDto.class))),
          @ApiResponse(responseCode = "400", description = "분실물 상세 조회 실패", content = @Content(schema = @Schema(implementation = LostSearchByIdResponseDto.class)))
  })
  @GetMapping("/lost/search/id")
  public ResponseEntity<?> getById(@RequestParam(value = "id") Long id) {
    try {
      LostSearchByIdResponseDto lostSearchByIdResponseDto = lostService.getById(id);
      return new ResponseEntity<>(lostSearchByIdResponseDto, HttpStatus.OK);
    } catch (IllegalStateException e) {
      return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
    }
  }
}