package com.server.mappin.repository;

import com.server.mappin.domain.Category;
import com.server.mappin.domain.Lost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LostRepository extends JpaRepository<Lost, Long> {
    @Query(value = "select l from Lost l left join fetch l.category c where c.name = :category")
    List<Lost> findByCategory(@Param("category") String category);

    @Query(value = "select l from Lost l left join fetch l.location c where c.dong = :dong")
    List<Lost> findLocationByDong(@Param("dong") String dong);
    @Override
    <S extends Lost> S save(S entity);
}


