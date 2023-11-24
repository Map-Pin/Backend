package com.server.mappin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomMember {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "chatroom_id")
  private ChatRoom chatRoom;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;
}
