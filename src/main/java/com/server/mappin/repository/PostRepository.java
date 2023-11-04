package com.server.mappin.repository;

import com.server.mappin.domain.Lost;
import com.server.mappin.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    <S extends Post> S save(S entity);
}
