package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ShopRegisterResponseDto {
  private int statusCode;
  private String isSuccess;
  private Long memberId;
  private Long shopId;
  private String dong;
  private String name;
  private String address;
  private String companyNumber;
}
