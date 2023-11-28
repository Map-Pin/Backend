package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class ShopRegisterRequestDto {
  private String name;
  private String address;
  private String companyNumber;

}
