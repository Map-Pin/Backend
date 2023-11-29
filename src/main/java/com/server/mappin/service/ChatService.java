package com.server.mappin.service;

import com.server.mappin.domain.ChatMessage;
import com.server.mappin.domain.ChatRoom;
import com.server.mappin.domain.ChatRoomMember;
import com.server.mappin.domain.Member;
import com.server.mappin.dto.ChatMessageDto;
import com.server.mappin.repository.ChatRepository;
import com.server.mappin.repository.ChatRoomMemberRepository;
import com.server.mappin.repository.ChatRoomRepository;
import com.server.mappin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatRepository chatRepository;
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    //채팅방 불러오기
    public List<ChatRoom> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
      return chatRoomRepository.findAll();

    }
    //채팅방 하나 불러오기
    public ChatRoom findById(String roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }
    //채팅방 생성
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        return chatRoomRepository.save(chatRoom);
    }


    public void send(ChatMessageDto message){
        System.out.println("message = " + message.toString());
        Member member = memberRepository.findByEmail(message.getSender()).orElseThrow(RuntimeException::new);
        ChatRoom chatRoom = chatRoomRepository.findByRoomId((message.getRoomId()));

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(member.getName()+"님이 입장하였습니다.");
            ChatRoomMember roomMember = ChatRoomMember.builder().chatRoom(chatRoom).member(member).build();
            chatRoomMemberRepository.save(roomMember);
        }
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);
        ChatMessage chatMessage = ChatMessage.builder()
                .message(message.getMessage())
                .type(message.getType())
                .roomId(message.getRoomId())
                .member(member)
                .createdAt(LocalDateTime.now())
                .build();

        chatRepository.save(chatMessage);
    }
}
