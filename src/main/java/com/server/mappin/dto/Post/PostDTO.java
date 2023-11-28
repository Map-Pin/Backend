package com.server.mappin.dto.Post;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PostDTO {
  @Data
  public static class PostCreateRQ {
    private String title;
    private String content;
    private String lostDate;
    private Double x;
    private Double y;
    private String category;

  }

  @Data
  @Builder
  public static class PostCreateRP {
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
  public static class PostSearchRP {
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
  public static class PostUpdateRQ {
    private String title;
    private String content;
    private String lostDate;
    private Double x;
    private Double y;
    private String category;
  }
  @Data
  @Builder
  public static class PostUpdateRP {
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
