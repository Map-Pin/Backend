package com.server.mappin.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class FindByDongResponseDto {
  private Long id;
  private String title;
  private LocalDateTime createdAt;
  private String imageUrl;
  private String dong;
}
