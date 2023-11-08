package com.server.mappin.dto;

import lombok.Data;

@Data
public class LostUpdateRequestDto {
  private String title;
  private String content;
  private String foundDate;
  private Double x;
  private Double y;
  private String dong;
  private String category;
}
