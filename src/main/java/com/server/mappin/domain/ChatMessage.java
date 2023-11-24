package com.server.mappin.domain;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  public enum MessageType {
    ENTER, TALK
  }

  private MessageType type;
  //채팅방 ID
  private String roomId;
  //보내는 사람
  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;
  //내용
  private String message;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

}
