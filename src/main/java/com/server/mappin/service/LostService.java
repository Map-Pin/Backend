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

  /*public FindByDongListResponseDto findByCurrentLocation(Double x, Double y) {
    String dong = mapService.getDong(x, y);
    Optional<Location> locationByDong = locationRepository.findLocationByDong(dong);
    if (locationByDong.isPresent()) {
      Location location = locationByDong.get();
      return findByDong(location.getDong());
    }
    return null;
  }
  */
    LocationDTO.LocationListRP findByCurrentLocation(Double x, Double y);

    LostDTO.LostRegisterRP registerLost(LostDTO.LostRegisterRQ lostRegisterRQ, MultipartFile image, String email) throws IOException;

    LostDTO.LostSearchByIdRP getById(Long id);
    LostDTO.LostUpdateRP update(Long lostId, LostDTO.LostUpdateRQ lostUpdateRQ, MultipartFile image, String email) throws IOException;


}