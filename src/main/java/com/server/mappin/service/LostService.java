package com.server.mappin.service;

import com.server.mappin.dto.Category.CategoryDTO;
import com.server.mappin.dto.Location.LocationDTO;
import com.server.mappin.dto.Lost.*;
import com.server.mappin.dto.Shop.ShopDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface LostService {
  CategoryDTO.CategoryListRP findByCategory(String categoryName);

  LocationDTO.LocationListRP findByDong(String dongName);

  ShopDTO.ShopListRP findByShop(String shopName);
}