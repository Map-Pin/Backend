package com.server.mappin.dto.Location;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class LocationDTO{
  @Builder
  @Data
  public static class LocationListRP {
      private List<LocationRP> result;
  }
  @Builder
  @Data
  public static class LocationRP {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private String imageUrl;
    private String dong;
  }

}
