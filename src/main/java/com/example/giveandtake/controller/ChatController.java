package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.ChatMessageDTO;
import com.example.giveandtake.model.entity.ChatMessage;
import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.service.ChatService;
import lombok.AllArgsConstructor;
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
    public List<ChatRoom> createRoom(@RequestParam String roomName, @RequestParam String receiver, Principal principal) {
        List<ChatRoom> chatRoom = chatService.createChatRoom(roomName,receiver,principal);
        return chatRoom;
    }

    //유저정보 조회 화면
    @GetMapping("/finduser/{txt}")
    public String finduser(Model model, @PathVariable String txt) {
        model.addAttribute("txt", txt);
        return "/chat/finduser";
    }


//     특정 채팅방 목록
    @GetMapping("/userList")
    @ResponseBody
    public List<User> users()
    {
            return chatService.findAllUsers();
    }

    //채팅그만두기 화면
    @GetMapping("/room/stop/{roomId}")
    public String stopChat(@PathVariable Long roomId)
    {
        chatService.deleteChatRoom(roomId);
        return "redirect:/chat/room";
    }







    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable Long roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public List<ChatRoom> roomInfo(@PathVariable Long roomId) {
        List<ChatRoom> chatRoom = chatService.findRoomById(roomId);
        return chatRoom;
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
