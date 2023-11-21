package com.server.mappin.dto.Category;

import com.server.mappin.dto.Location.LocationDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class CategoryDTO {
  @Builder
  @Data
  public static class CategoryListResponseDTO {
    private List<CategoryDTO.CategoryResponseDTO> result;
  }
  @Builder
  @Data
  public static class CategoryResponseDTO {
    private Long id;
    private String title;
    private LocalDateTime creatdAt;
    private String imageUrl;
  }
}
