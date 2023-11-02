package com.server.mappin.repository;

import com.server.mappin.domain.Lost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostRepository extends JpaRepository<Lost, Long> {
}
