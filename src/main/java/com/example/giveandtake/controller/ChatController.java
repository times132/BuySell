package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.ChatMessageDTO;
import com.example.giveandtake.model.entity.ChatMessage;
import com.example.giveandtake.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class ChatController {



    @Autowired
    private ChatService chatService;


    @MessageMapping("/chat/message")
    public void message(ChatMessageDTO chatMessageDTO) {
        System.out.println("###########################sendmessage");
        System.out.println(chatMessageDTO);

        chatService.createMessage(chatMessageDTO);

    }
}