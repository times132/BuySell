package com.example.giveandtake.service;

import com.example.giveandtake.DTO.ChatMessageDTO;
import com.example.giveandtake.DTO.ChatRoomDTO;
import com.example.giveandtake.model.entity.ChatMessage;
import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.repository.ChatMessageRepository;
import com.example.giveandtake.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final SimpMessageSendingOperations messagingTemplate;


    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public List<ChatRoom> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 반환
        System.out.println("findAllRoom");
        List<ChatRoom> chatList = chatRoomRepository.findAll();
        Collections.reverse(chatList);
        return chatList;
    }

    public List<ChatRoom> findRoomById(Long roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }

    //채팅방만들기
    public List<ChatRoom> createChatRoom(String roomName) {
        System.out.println("***************create room**************" + roomName);
        ChatRoomDTO chatRoomDTO =new ChatRoomDTO();
        chatRoomDTO.setRoomName(roomName);
        chatRoomRepository.save(chatRoomDTO.toEntity()).getRoomId();
        List<ChatRoom> chatRoom = chatRoomRepository.findByRoomName(roomName);
        return chatRoom;
    }


    public void createMessage(ChatMessageDTO chatMessageDTO) {

        if (ChatMessage.MessageType.ENTER.equals(chatMessageDTO.getType()))
            chatMessageDTO.setMessage(chatMessageDTO.getSender() + "님이 입장하셨습니다.");

        chatMessageRepository.save(chatMessageDTO.toEntity());

        messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessageDTO.getRoomId(), chatMessageDTO);
    }
}
