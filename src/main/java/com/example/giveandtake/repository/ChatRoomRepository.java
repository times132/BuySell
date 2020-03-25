package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

   ChatRoom findByRoomId(String roomId);

   List<ChatRoom> findALLByRoomId(String roomId);

}
