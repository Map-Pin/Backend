package com.server.mappin.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ChatRoom {
  @Id
  private String roomId;
  private String roomName;

  public static ChatRoom create(String name) {
    ChatRoom room = new ChatRoom();
    room.roomId = UUID.randomUUID().toString();
    room.roomName = name;
    return room;
  }
}