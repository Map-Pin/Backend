package com.server.mappin.dto.Category;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class CategoryDTO {
  @Builder
  @Data
  public static class CategoryListRP {
    private List<CategoryRP> result;
  }

  @Builder
  @Data
  public static class CategoryRP {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private String imageUrl;
    private String dong;
  }
}
