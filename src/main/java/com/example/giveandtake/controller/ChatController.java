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
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^SELECT ROOM");
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
       System.out.println("*****************************88"+ roomName + receiver);

        chatService.createChatRoom(roomName,receiver,principal);
        String result= "'"+roomName+"'" +"채팅방 개설이 완료되었습니다." ;

        return result;
    }

    //유저정보 조회 화면
    @GetMapping("/finduser/{txt}")
    public String finduser(Model model, @PathVariable String txt) {
        model.addAttribute("txt", txt);
        return "/chat/finduser";
    }


    //채팅그만두기 화면
    @GetMapping("/room/stop/{roomId}")
    public String stopChat(@PathVariable Long roomId, Principal principal)
    {
        chatService.deleteChatRoom(roomId, principal);
        return "redirect:/chat/room";
    }


    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable Long roomId) {
        model.addAttribute("roomId",roomId);
        return "/chat/roomdetail";
    }


    //메시지보내기
    @MessageMapping("/message")
    public void message(ChatMessageDTO chatMessageDTO) {
        System.out.println("###########################sendmessage");
        System.out.println(chatMessageDTO);
        chatService.createMessage(chatMessageDTO);
    }

    //메시지 조회
    @GetMapping("/messages/{roomId}")
    @ResponseBody
    public List<ChatMessage> MessageInfo(@PathVariable Long roomId) {
        System.out.println("**********************************MSG INFO***********************************" +roomId);

        return chatService.findMessages(roomId);
    }

}
