package com.example.giveandtake.service;

import com.example.giveandtake.DTO.ChatMessageDTO;
import com.example.giveandtake.DTO.ChatRoomDTO;
import com.example.giveandtake.common.CustomUserDetails;
import com.example.giveandtake.model.entity.ChatMessage;
import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.repository.ChatMessageRepository;
import com.example.giveandtake.repository.ChatRoomRepository;
import com.example.giveandtake.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatService {
    private final SimpMessageSendingOperations messagingTemplate;


    private ChatRoomRepository chatRoomRepository;
    private ChatMessageRepository chatMessageRepository;
    private UserRepository userRepository;


    // 채팅방 생성순서 최근 순으로 반환
    public List<ChatRoom> findAllRoom() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String my_id= ((CustomUserDetails) authentication.getPrincipal()).getUsername();
        List<ChatRoom> chatList = new ArrayList<>();
        chatList.addAll(chatRoomRepository.findByRequest(my_id));
        chatList.addAll(chatRoomRepository.findByReceiver(my_id));
        System.out.println("chatList"+chatList);
        Collections.reverse(chatList);
        return chatList;
    }

    public List<ChatRoom> findRoomById(Long roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }

    //채팅방만들기
    public List<ChatRoom> createChatRoom(String roomName, String receiver, Principal principal){
        System.out.println("***************create room**************" + roomName);
        ChatRoomDTO chatRoomDTO =new ChatRoomDTO();
        chatRoomDTO.setRoomName(roomName);
        chatRoomDTO.setReceiver(receiver);
//        List<User> userList = Collections.singletonList(userRepository.findByUsername(principal.getName()).get());
//        chatRoomDTO.setChatMembers(userList);
        chatRoomDTO.setRequest(principal.getName());
        chatRoomRepository.save(chatRoomDTO.toEntity()).getRoomId();


        List<ChatRoom> chatRoom = chatRoomRepository.findByRoomName(roomName);
        return chatRoom;
    }

    //대화내용 저장
    public void createMessage(ChatMessageDTO chatMessageDTO) {

        if (ChatMessage.MessageType.ENTER.equals(chatMessageDTO.getType())) {
            chatMessageDTO.setMessage(chatMessageDTO.getSender() + "님이 입장하셨습니다.");
            chatMessageDTO.setSender("[알림]");
        } else if (ChatMessage.MessageType.QUIT.equals(chatMessageDTO.getType())) {
            chatMessageDTO.setMessage(chatMessageDTO.getSender() + "님이 방에서 나갔습니다.");
            chatMessageDTO.setSender("[알림]");
        }
        Long msgNum = chatMessageRepository.save(chatMessageDTO.toEntity()).getMsgNum();
        Optional <ChatMessage> chatMessageList = chatMessageRepository.findByMsgNum(msgNum);
        ChatMessage chatMessage = chatMessageList.get();

        messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessageDTO.getRoomId(), chatMessage);


    }
    //채팅방 삭제
    public void deleteChatRoom(Long roomId) {

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%끝 delete"+roomId);

        chatRoomRepository.deleteById(roomId);

    }

    //채팅방 메시지 리스트 가져오기
    public List<ChatMessage> findMessages(Long roomId) {
        List<ChatMessage> messages = chatMessageRepository.findMessageByRoomId(roomId);
        return messages;
    }

    //유저정보가지고오기
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        System.out.println("**********실행"+ users);
        return users;
    }
}
