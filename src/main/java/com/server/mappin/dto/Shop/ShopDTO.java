package com.server.mappin.dto.Shop;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class ShopDTO {
  @Builder
  @Data
  public static class ShopListRP {
    private List<ShopRP> result;
  }
  @Builder
  @Data
  public static class ShopRP {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private String imageUrl;
    private String shopName;
  }

  @Builder
  @Data
  public static class ShopRegisterRQ {
    private String name;
    private String address;
    private String companyNumber;
  }
  @Builder
  @Data
  public static class ShopRegisterRP {
    private Long id;
    private Long memberId;
    private Long shopId;
    private String dong;
    private String name;
    private String address;
    private String companyNumber;
  }
}
