package com.server.mappin.service;

import com.server.mappin.dto.Shop.ShopDTO;

public interface ShopService {
  ShopDTO.ShopRegisterRP shopRegister(ShopDTO.ShopRegisterRQ shopRegisterRQ, String email);
}
