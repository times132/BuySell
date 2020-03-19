package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.ChatMessage;
import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
   List<ChatRoom> findByRoomId(String roomId);
   List<ChatRoom> findByRequest(String request);
   List<ChatRoom> findByReceiver(String receiver);

}
