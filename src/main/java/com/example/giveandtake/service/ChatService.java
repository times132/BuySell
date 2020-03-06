package com.example.giveandtake.service;

import com.example.giveandtake.DTO.ChatMessageDTO;
import com.example.giveandtake.DTO.ChatRoomDTO;
import com.example.giveandtake.DTO.ReplyDTO;
import com.example.giveandtake.common.CustomUserDetails;
import com.example.giveandtake.model.entity.ChatMessage;
import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.model.entity.Reply;
import com.example.giveandtake.repository.ChatMessageRepository;
import com.example.giveandtake.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserDefinedFileAttributeView;
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

    //채팅방만들기
    public List<ChatRoom> createChatRoom(String roomName, String receiver, Principal principal){
        System.out.println("***************create room**************" + roomName);
        ChatRoomDTO chatRoomDTO =new ChatRoomDTO();
        chatRoomDTO.setRoomName(roomName);
        chatRoomDTO.setReceiver(receiver);
        chatRoomDTO.setRequest(principal.getName());
        chatRoomRepository.save(chatRoomDTO.toEntity()).getRoomId();
        List<ChatRoom> chatRoom = chatRoomRepository.findByRoomName(roomName);
        return chatRoom;
    }

    // 모든 채팅방
    public List<ChatRoom> findAllRoom() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String my_id= ((CustomUserDetails) authentication.getPrincipal()).getUsername();
        List<ChatRoom> chatList = new ArrayList<>();
        chatList.addAll(chatRoomRepository.findByRequest(my_id));
        chatList.addAll(chatRoomRepository.findByReceiver(my_id));
        Collections.reverse(chatList);
        return chatList;
    }

    //특정채팅방 조회
    public List<ChatRoom> findRoomById(Long roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }
    //대화내용 저장
    public void createMessage(ChatMessageDTO chatMessageDTO) {

        if (ChatMessage.MessageType.QUIT.equals(chatMessageDTO.getType())) {
            chatMessageDTO.setMessage(chatMessageDTO.getSender() + "님이 방에서 나갔습니다.");
            chatMessageDTO.setSender("[알림]");

        }
        Long msgNum = chatMessageRepository.save(chatMessageDTO.toEntity()).getMsgNum();
        Optional <ChatMessage> chatMessageList = chatMessageRepository.findByMsgNum(msgNum);
        ChatMessage chatMessage = chatMessageList.get();

        messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessageDTO.getRoomId(), chatMessage);

    }
    //채팅방 삭제
    public void deleteChatRoom(Long roomId, Principal principal) {
        ChatRoomDTO chatRoomDTO =new ChatRoomDTO();
        List<ChatRoom> chatRoom = chatRoomRepository.findByRoomId(roomId);
        ChatRoom chats = chatRoom.get(0);

        String name = principal.getName();
        chatRoomDTO.setRoomId(roomId);
        chatRoomDTO.setRoomName(chats.getRoomName());
        chatRoomDTO.setReceiver(chats.getReceiver());
        chatRoomDTO.setRequest(chats.getRequest());

        if( name.equals(chats.getReceiver()) ){
            chatRoomDTO.setReceiver("");
            chatRoomRepository.save(chatRoomDTO.toEntity()).getRoomId();
        }
        if(name.equals(chats.getRequest())){
            chatRoomDTO.setRequest("");
            chatRoomRepository.save(chatRoomDTO.toEntity()).getRoomId();
        }
        if(chats.getRequest().equals("") && chats.getReceiver().equals("")) {
            chatRoomRepository.deleteById(roomId);
        }
    }

    //채팅방 메시지 리스트 가져오기
    public List<ChatMessage> findMessages(Long roomId) {
        List<ChatMessage> messages = chatMessageRepository.findMessageByRoomId(roomId);
        return messages;
    }


}
