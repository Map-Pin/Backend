package com.server.mappin.dto.Lost;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LostDTO {

  @Data
  @Builder
  public static class LostRegisterResponseDto {
    private Long memberId;
    private Long lostId;
    private String title;
    private String content;
    private LocalDate foundDate;
    private LocalDateTime createdAt;
    private String image;
    private Double x;
    private Double y;
    private String dong;
    private String category;
  }

  @Data
  public static class LostRegisterRequestDto {
    private String title;
    private String content;
    private String foundDate;
    private Double x;
    private Double y;
    private String dong;
    private String category;
  }

  @Builder
  @Data
  public static class LostSearchByIdResponseDto {
    private String title;
    private String content;
    private LocalDate foundDate;
    private LocalDateTime createdAt;
    private String image;
    private Double x;
    private Double y;
    private String dong;
    private String category;
  }

  @Data
  public static class LostUpdateRequestDto {
    private String title;
    private String content;
    private String foundDate;
    private Double x;
    private Double y;
    private String dong;
    private String category;
  }

  @Data
  @Builder
  public static class LostUpdateResponseDto {
    private Long memberId;
    private Long lostId;
    private String title;
    private String content;
    private LocalDate foundDate;
    private LocalDateTime createdAt;
    private String image;
    private Double x;
    private Double y;
    private String dong;
    private String category;
  }
}
