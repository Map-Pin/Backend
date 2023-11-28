package com.server.mappin.controller;

import com.server.mappin.dto.LostRegisterRequestDto;
import com.server.mappin.dto.LostRegisterResponseDto;
import com.server.mappin.dto.ShopRegisterRequestDto;
import com.server.mappin.dto.ShopRegisterResponseDto;
import com.server.mappin.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Shop API", description = "가게 관련 API 명세서")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ShopController {
  private final ShopService shopService;

  @Operation(summary = "가게 등록", description = "application/json입니다")
  @ApiResponse(content = @Content(schema = @Schema(implementation = ShopRegisterResponseDto.class)))
  @PutMapping(value = "/shop/register")
  public ResponseEntity<?> registerLost(
          @RequestBody ShopRegisterRequestDto shopRegisterRequestDto,
          Authentication authentication) throws IOException {
    try {
      ShopRegisterResponseDto shopRegisterResponseDto = shopService.shopRegister(shopRegisterRequestDto, authentication.getName());
      return new ResponseEntity<>(shopRegisterResponseDto, HttpStatus.OK);
    } catch (IllegalStateException e) {
      return new ResponseEntity<>("에러가 발생했습니다", HttpStatus.CONFLICT);
    }
  }
}
