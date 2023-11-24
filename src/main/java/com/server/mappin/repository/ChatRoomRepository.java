package com.server.mappin.repository;

import com.server.mappin.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
  @Query(value = "select c from ChatRoom c where c.roomId = :roomId")
  ChatRoom findByRoomId(@Param("roomId") String roomId);
}
