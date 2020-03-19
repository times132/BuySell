package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
    Optional<ChatMessage> findByMsgNum(Long msgNum);
    List<ChatMessage> findMessageByRoomId(String roomId);
}
