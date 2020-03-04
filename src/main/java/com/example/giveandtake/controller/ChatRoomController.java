package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.ChatRoomDTO;
import com.example.giveandtake.model.entity.ChatMessage;
import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GenerationType;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    @Autowired
    private ChatService chatService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }


    //채팅그만두기 화면
    @GetMapping("/room/stop/{roomId}")
    public String stopChat(@PathVariable Long roomId)
    {
        chatService.deleteChatRoom(roomId);
        return "redirect:/chat/room";
    }

    // 모든 채팅방 목록
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatService.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public List<ChatRoom> createRoom(@RequestParam String roomName, @RequestParam String receiver, Principal principal) {
        List<ChatRoom> chatRoom = chatService.createChatRoom(roomName,receiver,principal);
        return chatRoom;
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

    //메시지 조회
//    @GetMapping("/messages/{roomId}")
//    @ResponseBody
//    public List<ChatMessage> MessageInfo(@PathVariable Long roomId) {
//        System.out.println("1.통과***********************************" +roomId);
//        List<ChatMessage> chatMessages = chatService.findMessages(roomId);
//        return chatMessages;
//    }

}
