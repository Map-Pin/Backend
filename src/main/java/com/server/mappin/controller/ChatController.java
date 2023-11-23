package com.server.mappin.controller;

import com.server.mappin.domain.ChatRoom;
import com.server.mappin.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
  private final ChatService chatService;
  // 모든 채팅방 목록 반환
  @GetMapping("/rooms")
  public List<ChatRoom> room() {
    System.out.println(" room exists? = ");
    return chatService.findAllRoom();
  }
  // 채팅방 생성
  @PostMapping("/room")
  public ChatRoom createRoom(@RequestParam String name) {
    return chatService.createRoom(name);
  }
  // 특정 채팅방 조회
  @GetMapping("/room/{roomId}")
  public ChatRoom roomInfo(@PathVariable String roomId) {
    return chatService.findById(roomId);
  }
}
