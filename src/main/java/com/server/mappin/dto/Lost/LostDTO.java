package com.server.mappin.dto.Lost;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LostDTO {

  @Data
  @Builder
  public static class LostRegisterRP {
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
  public static class LostRegisterRQ {
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
  public static class LostSearchByIdRP {
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
  public static class LostUpdateRQ {
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
  public static class LostUpdateRP {
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
