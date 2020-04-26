package com.buysell.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class ChatMessageRepositoryTest {


    @Autowired
    private ChatMessageRepository chatMessageRepository;

//    @Test
//    public void findMessage(){
//
//        List<ChatMessage> chatList = chatMessageRepository.findMessageByRoomId(Long.valueOf(49));
//
//        ChatMessage chats = chatList.get(0);
//        System.out.println(chatList.get(0));
//        assertThat(chats.getMsgNum(), is(378L));
//    }

}
