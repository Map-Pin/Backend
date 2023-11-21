package com.server.mappin.dto.Location;

import com.server.mappin.dto.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class LocationDTO{
  @Builder
  @Data
  public static class LocationListResponseDTO {
      private List<LocationResponseDTO> result;
  }
  @Builder
  @Data
  public static class LocationResponseDTO {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private String imageUrl;
    private String dong;
  }

}
