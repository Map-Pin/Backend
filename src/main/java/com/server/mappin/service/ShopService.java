package com.server.mappin.service;

import com.server.mappin.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {
  private final ShopRepository shopRepository;

  @Transactional
  public ShopRegisterResponseDto shopRegister() {

  }
}
