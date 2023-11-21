package com.server.mappin.dto.Post;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PostDTO {
  @Data
  public static class PostCreateRequestDto {
    private String title;
    private String content;
    private String lostDate;
    private Double x;
    private Double y;
    private String category;

  }

  @Data
  @Builder
  public static class PostCreateResponseDto {
    private Long memberId;
    private Long postId;
    private String title;
    private String content;
    private LocalDate lostDate;
    private LocalDateTime createdAt;
    private String image;
    private Double x;
    private Double y;
    private String dong;
    private String category;
  }

  @Builder
  @Data
  public static class PostSearchResponseDto {
    private String title;
    private String content;
    private LocalDate lostDate;
    private LocalDateTime createdAt;
    private String image;
    private Double x;
    private Double y;
    private String dong;
    private String category;
  }

  @Data
  public static class PostUpdateRequestDto {
    private String title;
    private String content;
    private String lostDate;
    private Double x;
    private Double y;
    private String category;
  }
  @Data
  @Builder
  public static class PostUpdateResponseDto {
    private Long memberId;
    private Long postId;
    private String title;
    private String content;
    private LocalDate lostDate;
    private LocalDateTime createdAt;
    private String image;
    private Double x;
    private Double y;
    private String dong;
    private String category;
  }
}
