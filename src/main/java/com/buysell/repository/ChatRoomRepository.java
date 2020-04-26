package com.buysell.repository;

import com.buysell.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

   ChatRoom findByRoomId(String roomId);
   List<ChatRoom> findALLByRoomId(String roomId);
}
