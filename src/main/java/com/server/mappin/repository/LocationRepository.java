package com.server.mappin.repository;

import com.server.mappin.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findLocationByDong(String dong);

}
