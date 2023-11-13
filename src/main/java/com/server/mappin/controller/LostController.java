package com.server.mappin.controller;

import com.server.mappin.dto.BaseResponseDto;
import com.server.mappin.dto.Category.CategoryDTO;
import com.server.mappin.dto.Location.LocationDTO;
import com.server.mappin.dto.Lost.*;
import com.server.mappin.dto.Shop.ShopDTO;
import com.server.mappin.service.LostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Lost API", description = "분실물 관련 API 명세서")
@RestController
@Slf4j
@RequiredArgsConstructor
public class LostController {
  private final LostService lostService;

  @Operation(summary = "분실물 등록", description = "Content-Type은 multipart/form-data이지만 info는 application/json입니다")
  @ApiResponse(content = @Content(schema = @Schema(implementation = BaseResponseDto.class)))
  @PutMapping(value = "/lost/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public BaseResponseDto<LostDTO.LostRegisterResponseDto> registerLost(
          @RequestPart("image") MultipartFile file,
          @RequestPart("info") LostDTO.LostRegisterRequestDto lostRegisterRequestDto,
          Authentication authentication) throws IOException {
    try {
      LostDTO.LostRegisterResponseDto lostRegisterResponseDto = lostService.registerLost(lostRegisterRequestDto,file, authentication.getName());
      return BaseResponseDto.of("success", 200, lostRegisterResponseDto);
    } catch (IllegalStateException e) {
      return BaseResponseDto.onFailure("failure", 400,null);
    }
  }

  @Operation(summary = "분실물 수정",description = "Content-type은 multipart/form-data이지만 info는 application/json입니다")
  @ApiResponses({
          @ApiResponse(responseCode ="200",description ="분실물 수정 성공",content = @Content(schema = @Schema(implementation = LostDTO.LostUpdateResponseDto.class))),
          @ApiResponse(responseCode ="400",description ="분실물 수정 실패",content = @Content(schema = @Schema(implementation = LostDTO.LostUpdateResponseDto.class)))
  })
  @PatchMapping(value = "/lost/update/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public BaseResponseDto<LostDTO.LostUpdateResponseDto> update(
          @PathVariable("id") Long id,
          @RequestPart("image") MultipartFile image,
          @RequestPart("info") LostDTO.LostUpdateRequestDto lostUpdateRequestDto,
          Authentication authentication
  ) throws IOException {
    try{
      String email = authentication.getName();
      LostDTO.LostUpdateResponseDto lostUpdateResponseDto = lostService.update(id, lostUpdateRequestDto, image, email);
      return BaseResponseDto.of("success", 200, lostUpdateResponseDto);
    } catch (IllegalStateException e) {
      return BaseResponseDto.onFailure("failure", 400,null);
    }
  }

  @Operation(summary = "카테고리 검색", description = "분실물을 카테고리 별로 검색")
  @ApiResponse(content = @Content(schema = @Schema(implementation = CategoryDTO.CategoryListResponseDTO.class)))
  @GetMapping("lost/search/category")
  public BaseResponseDto<List<CategoryDTO.CategoryResponseDTO>> searchByCategory(@RequestParam(value = "category") String name) {
    try {
      CategoryDTO.CategoryListResponseDTO categoryListResponseDTO = lostService.findByCategory(name);
      return BaseResponseDto.of("success", 200, categoryListResponseDTO.getResult());
    } catch (IllegalStateException e) {
      return BaseResponseDto.onFailure("failure", 400,null);
    }
  }

  @Operation(summary = "동 검색", description = "분실물을 동 별로 검색")
  @ApiResponse(content = @Content(schema = @Schema(implementation = LocationDTO.LocationListResponseDTO.class)))
  @GetMapping("lost/search/dong")
  public BaseResponseDto<List<LocationDTO.LocationResponseDTO>> searchByDong(@RequestParam(value = "name") String dongName) {
    try {
      LocationDTO.LocationListResponseDTO locationListResponseDTO = lostService.findByDong(dongName);
      return BaseResponseDto.of("success", 200, locationListResponseDTO.getResult());
    } catch (IllegalStateException e) {
      return BaseResponseDto.onFailure("failure", 400,null);
    }
  }

  @Operation(summary = "가게 검색", description = "분실물을 가게이름 별로 검색")
  @ApiResponse(content = @Content(schema = @Schema(implementation = ShopDTO.ShopListResponseDTO.class)))
  @GetMapping("lost/search/shop")
  public BaseResponseDto<List<ShopDTO.ShopResponseDTO>> searchByShop(@RequestParam(value = "name") String shopName) {
    try {
      ShopDTO.ShopListResponseDTO byShop = lostService.findByShop(shopName);
      return BaseResponseDto.of("success", 200, byShop.getResult());
    } catch (IllegalStateException e) {
      return BaseResponseDto.onFailure("failure", 400,null);
    }
  }

  @Operation(summary = "현재위치로 검색", description = "분실물을 현재위치로 검색")
  @ApiResponse(content = @Content(schema = @Schema(implementation = LocationDTO.LocationListResponseDTO.class)))
  @GetMapping("lost/search/location")
  public BaseResponseDto<List<LocationDTO.LocationResponseDTO>> searchByCurrentLocation(
          @RequestParam(value = "x") Double x,
          @RequestParam(value = "y") Double y
  ) {
    try {
      LocationDTO.LocationListResponseDTO byDong = lostService.findByCurrentLocation(x, y);
      return BaseResponseDto.of("success", 200, byDong.getResult());
    } catch (IllegalStateException e) {
      return BaseResponseDto.onFailure("failure", 400,null);
    }
  }

  @Operation(summary = "분실물 상세 조회", description = "분실물 Id로 분실물 상세 조회")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "분실물 상세 조회 성공", content = @Content(schema = @Schema(implementation = LostDTO.LostSearchByIdResponseDto.class))),
          @ApiResponse(responseCode = "400", description = "분실물 상세 조회 실패", content = @Content(schema = @Schema(implementation = LostDTO.LostSearchByIdResponseDto.class)))
  })
  @GetMapping("/lost/search/id")
  public BaseResponseDto<LostDTO.LostSearchByIdResponseDto> getById(@RequestParam(value = "id") Long id) {
    try {
      LostDTO.LostSearchByIdResponseDto lostSearchByIdResponseDto = lostService.getById(id);
      return BaseResponseDto.of("success", 200, lostSearchByIdResponseDto);
    } catch (IllegalStateException e) {
      return BaseResponseDto.onFailure("failure", 400,null);
    }
  }
}