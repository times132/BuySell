package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.ChatRoom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class ChatRoomRepositoryTest {
    @Autowired
    private ChatRoomRepository chatRepository;

    @Test
    public void create(){
        chatRepository.save(ChatRoom.builder()
                .roomName("안녕")
                .build());

        List<ChatRoom> chatList = chatRepository.findAll();

        ChatRoom chats = chatList.get(0);
        assertThat(chats.getRoomName(), is("안녕"));
    }

}