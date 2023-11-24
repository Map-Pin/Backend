package com.server.mappin.controller;

import com.server.mappin.domain.ChatMessage;
import com.server.mappin.dto.BaseResponseDto;
import com.server.mappin.dto.ChatMessageDto;
import com.server.mappin.repository.ChatRepository;
import com.server.mappin.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

  private final ChatService chatService;

  @MessageMapping("/chat/message")
  public BaseResponseDto<Object> enter(ChatMessageDto message, Authentication authentication) {
    //System.out.println("AUTENNT: " + authentication.getName());
    chatService.send(message);
    return BaseResponseDto.of("success", 200, null);
  }
}
