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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("chat")
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
    public List<ChatRoom> roomInfo(@PathVariable String roomId) {
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
    @PostMapping(value = "/room", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE}) //json 방식으로 데이터를 받음
    public ResponseEntity<String> createRoom(@RequestBody ChatRoomDTO chatRoomDTO){
        return new ResponseEntity<>(chatService.createChatRoom(chatRoomDTO), HttpStatus.OK);
    }


    //채팅방 삭제
    @GetMapping("/room/stop/{roomId}")
    public String remove(Principal principal,@PathVariable String roomId){
        System.out.println("DELETE ROOM");
        chatService.deleteChatRoom(roomId, principal);
        return "redirect:/chat/room";
    }
    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId, Principal principal, HttpServletResponse response)throws IOException
    {
        if(chatService.checkAccess(principal, roomId)) {
            model.addAttribute("roomId", roomId);
            System.out.println("true");
        }
        else {
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('부적절한 시도를 하였습니다.');location.href='/chat/room';</script>");
            out.flush();

        }
        return "/chat/room";
    }


    //메시지보내기
    @MessageMapping("/message")
    @SendToUser
    public void message(ChatMessageDTO chatMessageDTO, Principal principal) {
        chatService.createMessage(chatMessageDTO ,principal);

    }

    //메시지 조회-입장시
    @GetMapping("/messages/{roomId}")
    @ResponseBody
    public List<ChatMessage> MessageInfo(@PathVariable String roomId, Principal principal) {
        if(chatService.checkAccess(principal, roomId)) {
            return chatService.findMessages(roomId, principal);
        }
        return null;
    }

}
