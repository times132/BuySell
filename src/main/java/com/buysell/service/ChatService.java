package com.buysell.service;

import com.buysell.domain.DTO.BoardDTO;
import com.buysell.domain.DTO.ChatRoomDTO;
import com.buysell.mapper.ChatMapper;
import com.buysell.repository.ChatMessageRepository;
import com.buysell.repository.ChatRoomRepository;
import com.buysell.repository.ChatUsersRepository;
import com.buysell.repository.UserRepository;
import com.buysell.domain.DTO.ChatMessageDTO;
import com.buysell.domain.DTO.ChatUsersDTO;
import com.buysell.security.CustomUserDetails;
import com.buysell.domain.entity.ChatMessage;
import com.buysell.domain.entity.ChatRoom;
import com.buysell.domain.entity.ChatUsers;
import com.buysell.domain.entity.User;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

import static com.buysell.domain.entity.ChatMessage.MessageType.BOARD;

@Service
@AllArgsConstructor
public class ChatService{
    private final SimpMessageSendingOperations messagingTemplate;

    private ChatMapper chatMapper;
    private ChatRoomRepository chatRoomRepository;
    private ChatMessageRepository chatMessageRepository;
    private UserRepository userRepository;
    private ChatUsersRepository chatUsersRepository;

    //채팅방만들기
    @Transactional
    public String createChatRoom(String nickname, Principal principal){
            User me = userRepository.findByNickname(principal.getName());
            User receiver = userRepository.findByNickname(nickname);

            //채팅방 중복검사
            List<ChatUsers> chatList = chatUsersRepository.findAllByUserNickname(principal.getName()); //본인이 속해 있는 모든 채팅방 정보 SELECT

            for (ChatUsers chatUsers : chatList){
                ChatRoom chatRoom = chatUsers.getChatRoom();

                List<ChatUsers> users = chatRoom.getUsers();
                //해당 채팅방 userList 검색
                for (ChatUsers user : users){
                    if (user.getUser().getNickname().equals(nickname)){
                        return user.getChatRoom().getRoomId();
                    }
                }
            }

            //USER 목록 생성
            List<User> participant = new ArrayList<User>();
            participant.add(me);
            participant.add(receiver);

            ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
            String randomId = UUID.randomUUID().toString();
            chatRoomDTO.setRoomId(randomId);
            chatRoomDTO.setMsgDate(LocalDateTime.now());
            ChatRoom chatRoom = chatRoomRepository.save(chatRoomDTO.toEntity());
            //Chat User
            for(User part : participant){
                ChatUsersDTO dto = new ChatUsersDTO();
                    dto.setChatRoom(chatRoom);
                    dto.setUser(part);
                ChatUsers chatUsers = chatUsersRepository.save(chatMapper.userToEntity(dto));
                chatRoom.getUsers().add(chatUsers);
            }

            return randomId;
    }

    // 모든 채팅방 조회
    @Transactional
    public List<ChatRoom> findAllRoom() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User me = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        List<ChatUsers> chatUsers = chatUsersRepository.findAllByUserNickname(me.getNickname()); //본인이 속해있는 모든 채팅방 정보 SELECT

        ArrayList chatRooms= new ArrayList<>();
        for (ChatUsers chatUser : chatUsers){
            chatRooms.add(chatUser.getChatRoom());
        }
        Collections.sort(chatRooms); //시간순서대로 정렬
        Collections.reverse(chatRooms); //가장 최근 순으로 반환

        return chatRooms;
    }

    //특정채팅방 조회
    public List<ChatRoom> findRoomById(String roomId) {
        return chatRoomRepository.findALLByRoomId(roomId);
    }

    //대화내용 저장
    @Transactional
    public void createMessage(ChatMessageDTO chatMessageDTO, Principal principal) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(chatMessageDTO.getRoomId());
        List<ChatUsers> users = chatRoom.getUsers();

        if (ChatMessage.MessageType.QUIT.equals(chatMessageDTO.getType())) {
            chatMessageDTO.setMessage(chatMessageDTO.getSender() + "님이 방에서 나갔습니다.");
            chatMessageDTO.setSender("[알림]");
            if (users.size() == 1){ return;
            }
        }

        chatMessageDTO.setChatRoom(chatRoom);
        ChatMessage chatMessage = chatMessageRepository.save(chatMessageDTO.toEntity()); //메시지 DB저장

        List<ChatMessage> messages = chatRoom.getMessages();
        messages.add(chatMessage);
        ChatRoomDTO chatRoomDTO = chatMapper.RoomToDto(chatRoom);
        chatRoomDTO.setMessages(messages);
        chatRoomDTO.setMsgDate(chatMessage.getCreatedDate()); //최근 메세지 시간을 채팅방 시간으로 입력
        chatRoomDTO.setRecentMsg(chatMessage.getMessage()); //최근 메세지 내용으로 설정
        String nickname = principal.getName();


        String to = null;
        //내가 보낸 메시지 개수 설정
        for (ChatUsers user : users){
            if (user.getUser().getNickname().equals(nickname)){
                ChatUsersDTO chatUsersDTO = chatMapper.toDTO(user);
                chatUsersDTO.setMsgCount(user.getMsgCount()+1);  ////내가 보낸 메시지 수 +1
                chatUsersRepository.save(chatMapper.userToEntity(chatUsersDTO));
            }
            else {
                to= user.getUser().getNickname();
            }
        }
        chatRoomRepository.save(chatRoomDTO.toEntity());
        messagingTemplate.convertAndSend("/queue/chat/room/" + chatMessageDTO.getRoomId(), chatMessage);
        messagingTemplate.convertAndSendToUser(to,"/queue/chat/room", chatMessage);
    }

    //채팅방 삭제
    public void deleteChatRoom(String roomId, String nickName) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        List<ChatUsers> users = chatRoom.getUsers();
        if (users.size() == 1){
            chatRoomRepository.deleteById(roomId);
            return;
        }
        for (ChatUsers user : users){
            if (user.getUser().getNickname().equals(nickName)){
                chatUsersRepository.deleteUserById(user.getCid());
            }
        }
    }

    //채팅방 대화 내용 모두 가져오기
    public List<ChatMessage> findMessages(String roomId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User me = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        List<ChatMessage> messages = chatRoom.getMessages();
        List<ChatUsers> users = chatRoom.getUsers();

        //메세지 수 0
        for (ChatUsers user : users){
            if (!user.getUser().getNickname().equals(me.getNickname())){
                ChatUsersDTO chatUsersDTO = chatMapper.toDTO(user);
                chatUsersDTO.setMsgCount(0);  //상대방이 보낸 메세지 개수 0 으로 설정
                chatUsersRepository.save(chatMapper.userToEntity(chatUsersDTO));
            }
        }
        return messages;
    }

    public boolean checkAccess(String roomId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User me = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        List<ChatUsers> chatUsers = chatUsersRepository.findAllByUserNickname(me.getNickname()); //본인이 속해있는 모든 채팅방 정보 SELECT
        for (ChatUsers chatUser : chatUsers){
            if (chatUser.getChatRoom().getRoomId().equals(roomId)){return true; }
        }
        return false;
    }
    @Transactional
    public String convertBoardToMessage(BoardDTO boardDTO, Long id, Principal principal, String roomId) {
        BoardDTO boardMsg = new BoardDTO();
        boardMsg.setBid(boardDTO.getBid());
        if (boardDTO.getContent().length() >= 10) {
            boardMsg.setContent(boardDTO.getContent().substring(0,10));
        }
        else{
            boardMsg.setContent(boardDTO.getContent());
        }
        boardMsg.setTitle(boardDTO.getTitle());
        boardMsg.setPrice(boardDTO.getPrice());
        boardMsg.setCreatedDate(boardDTO.getCreatedDate());

        Gson gson = new Gson();
        String json = gson.toJson(boardMsg);
        ChatMessageDTO chatMessageDTO =new ChatMessageDTO();
        chatMessageDTO.setSender(principal.getName());
        chatMessageDTO.setSenderId(id.toString());
        chatMessageDTO.setRoomId(roomId);
        chatMessageDTO.setType(BOARD);
        chatMessageDTO.setMessage(json);
        createMessage(chatMessageDTO ,principal);

        return roomId;
    }
}
