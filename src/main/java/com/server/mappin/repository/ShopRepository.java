package com.server.mappin.repository;

import com.server.mappin.domain.Member;
import com.server.mappin.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
  Optional<Shop> findByMember(Member member);
}