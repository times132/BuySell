package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.ChatMessageDTO;
import com.example.giveandtake.DTO.ChatRoomDTO;
import com.example.giveandtake.DTO.ReplyDTO;
import com.example.giveandtake.model.entity.ChatMessage;
import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.service.ChatService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.Dump;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/chat")
@MessageMapping("/chat")
public class ChatController {

    private ChatService chatService;


    // 모든 채팅방 목록
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatService.findAllRoom();
    }
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public List<ChatRoom> roomInfo(@PathVariable Long roomId) {
        return chatService.findRoomById(roomId);
    }


    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    // 채팅 리스트 화면
    @GetMapping("/rooom/{nickName}")
    public String nicknameinrooms(Model model,@PathVariable String nickName) {
        model.addAttribute("nickName", nickName);
        return "/chat/room";
    }


    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public String createRoom(@RequestParam String roomName, @RequestParam String receiver, Principal principal) {
        chatService.createChatRoom(roomName,receiver,principal);
        String result= "'"+roomName+"'" +"채팅방 개설이 완료되었습니다." ;

        return result;
    }


    //채팅그만두기 화면
    @GetMapping("/room/stop/{roomId}")
    public String stopChat(@PathVariable Long roomId, Principal principal)
    {
        System.out.println("DELETE ROOM");
        chatService.deleteChatRoom(roomId, principal);
        return "redirect:/chat/rooms";
    }


    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable Long roomId) {
        model.addAttribute("roomId",roomId);
        return "/chat/room";
    }


    //메시지보내기
    @MessageMapping("/message")
    public void message(ChatMessageDTO chatMessageDTO) {
        chatService.createMessage(chatMessageDTO);

    }

    //메시지 조회
    @GetMapping("/messages/{roomId}")
    @ResponseBody
    public List<ChatMessage> MessageInfo(@PathVariable Long roomId) {
        return chatService.findMessages(roomId);
    }

}
