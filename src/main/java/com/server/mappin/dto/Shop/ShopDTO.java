package com.server.mappin.dto.Shop;

import com.server.mappin.dto.Location.LocationDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class ShopDTO {
  @Builder
  @Data
  public static class ShopListResponseDTO {
    private List<ShopDTO.ShopResponseDTO> result;
  }
  @Builder
  @Data
  public static class ShopResponseDTO {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private String imageUrl;
    private String shopName;
  }

  @Builder
  @Data
  public static class ShopRegisterRequestDto{
    private String name;
    private String address;
    private String companyNumber;
  }
  @Builder
  @Data
  public static class ShopRegisterResponseDto{
    private Long id;
    private Long memberId;
    private Long shopId;
    private String dong;
    private String name;
    private String address;
    private String companyNumber;
  }
}
