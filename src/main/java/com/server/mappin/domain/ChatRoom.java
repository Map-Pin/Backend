package com.server.mappin.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ChatRoom {
  @Id
  @Column(name = "chatroom_id")
  private String roomId;
  private String roomName;
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public static ChatRoom create(String name) {
    ChatRoom room = new ChatRoom();
    room.roomId = UUID.randomUUID().toString();
    room.roomName = name;
    room.createdAt = LocalDateTime.now();
    return room;
  }
}