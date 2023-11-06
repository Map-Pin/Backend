package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindByDongResponseDto {
  private Long id;
  private String title;
  private String dong;
}
