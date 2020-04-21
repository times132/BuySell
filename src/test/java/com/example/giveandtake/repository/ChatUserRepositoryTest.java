package com.example.giveandtake.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
@SpringBootTest
public class ChatUserRepositoryTest {
    @Autowired
    private ChatUsersRepository chatUserRepository;

    @Test
    public void deleteChatUser(){
        chatUserRepository.deleteById(10L);
    }
}
