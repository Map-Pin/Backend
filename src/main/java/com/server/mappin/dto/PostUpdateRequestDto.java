package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PostUpdateRequestDto {
  private String title;
  private String content;
  private String lostDate;
  private Double x;
  private Double y;
  private String category;
}
