package com.example.giveandtake.service;

import com.example.giveandtake.DTO.ChatMessageDTO;
import com.example.giveandtake.DTO.ChatRoomDTO;
import com.example.giveandtake.common.CustomUserDetails;
import com.example.giveandtake.model.entity.ChatMessage;
import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.repository.ChatMessageRepository;
import com.example.giveandtake.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ChatService{
    private final SimpMessageSendingOperations messagingTemplate;

    private ChatRoomRepository chatRoomRepository;
    private ChatMessageRepository chatMessageRepository;
    private UserService userService;
    //채팅방만들기
    public String createChatRoom(ChatRoomDTO chatRoomDTO){

            String status = "ok";
            //닉네임 존재여부 확인
            int result = userService.usernameCheck(chatRoomDTO.getReceiver());
            if(result == 0){
                status = "존재하지 않는 닉네임입니다.";
                return status;
            }
            //채팅방 중복검사
            List<ChatRoom> chatList = new ArrayList<>();
            chatList.addAll(chatRoomRepository.findByRequest(chatRoomDTO.getRequest()));
            chatList.addAll(chatRoomRepository.findByReceiver(chatRoomDTO.getRequest()));
            for (ChatRoom chatRoom: chatList){
                if(chatRoom.getReceiver().equals(chatRoomDTO.getReceiver()) || chatRoom.getRequest().equals(chatRoomDTO.getReceiver())){
                    status = "채팅방이 이미 존재합니다.";
                }
            }

            if (status.equals("ok")){
                status = "채팅방 개설이 완료되었습니다.";
                chatRoomDTO.setMsgDate(LocalDateTime.now());
                chatRoomRepository.save(chatRoomDTO.toEntity()).getRoomId();
            }
            System.out.println("###############"+status);
            return status;
    }

    // 모든 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String my_id= ((CustomUserDetails) authentication.getPrincipal()).getUsername();
        ArrayList chatList = new ArrayList<>();
        chatList.addAll(chatRoomRepository.findByRequest(my_id));
        chatList.addAll(chatRoomRepository.findByReceiver(my_id));

        Collections.sort(chatList); //시간순서대로 정렬
        Collections.reverse(chatList); //가장 최근 순으로 반환
        return chatList;
    }

    //특정채팅방 조회
    public List<ChatRoom> findRoomById(Long roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }


    //대화내용 저장
    public void createMessage(ChatMessageDTO chatMessageDTO, Principal principal) {
        if (ChatMessage.MessageType.QUIT.equals(chatMessageDTO.getType())) {
            chatMessageDTO.setMessage(chatMessageDTO.getSender() + "님이 방에서 나갔습니다.");
            chatMessageDTO.setSender("[알림]");
        }

        Long msgNum = chatMessageRepository.save(chatMessageDTO.toEntity()).getMsgNum();
        Optional <ChatMessage> chatMessageList = chatMessageRepository.findByMsgNum(msgNum);
        ChatMessage chatMessage = chatMessageList.get();
        List<ChatRoom> chatRooms = chatRoomRepository.findByRoomId(chatMessage.getRoomId());
        ChatRoom chats = chatRooms.get(0);
        ChatRoomDTO chatRoomDTO = convertEntityToDto(chats);
        chatRoomDTO.setMsgDate(chatMessage.getCreatedDate()); //최근 메세지 시간을 채팅방 시간으로 입력
        chatRoomDTO.setRoomName(chatMessage.getMessage());
        String name = principal.getName();
        //메시지 개수 설정
        if(name.equals(chats.getReceiver())){
            int num = chats.getRcMsgCount();
            chatRoomDTO.setRcMsgCount(num+1); //나의 메시지 개수 증가
        }
        else {
            int num = chats.getRqMsgCount();
            chatRoomDTO.setRqMsgCount(num+1);
        }
        chatRoomRepository.save(chatRoomDTO.toEntity()).getRoomId();
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessageDTO.getRoomId(), chatMessage);

    }
    //채팅방 삭제
    public void deleteChatRoom(Long roomId, Principal principal) {
        List<ChatRoom> chatRoom = chatRoomRepository.findByRoomId(roomId);
        ChatRoom chats = chatRoom.get(0);
        ChatRoomDTO chatRoomDTO = convertEntityToDto(chats);
        String name = principal.getName();

        if(name.equals(chats.getReceiver()) ){
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
    public List<ChatMessage> findMessages(Long roomId, Principal principal) {
        List<ChatMessage> messages = chatMessageRepository.findMessageByRoomId(roomId);
        String name = principal.getName();
        List<ChatRoom> chatRooms = chatRoomRepository.findByRoomId(roomId);
        ChatRoom chats = chatRooms.get(0);
        ChatRoomDTO chatRoomDTO = convertEntityToDto(chats);
        //메시지 개수 설정
        if(name.equals(chats.getReceiver())){
            chatRoomDTO.setRqMsgCount(0); //상대방이 보낸 메세지 개수 리셋
        }
        else{
            chatRoomDTO.setRcMsgCount(0);
        }
        chatRoomRepository.save(chatRoomDTO.toEntity()).getRoomId();

        return messages;
    }

    private ChatRoomDTO convertEntityToDto(ChatRoom chatRoom){
        return ChatRoomDTO.builder()
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .receiver(chatRoom.getReceiver())
                .request(chatRoom.getRequest())
                .rcMsgCount(chatRoom.getRcMsgCount())
                .rqMsgCount(chatRoom.getRqMsgCount())
                .msgDate(chatRoom.getMsgDate())
                .build();
    }


}
