package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.model.entity.ChatUsers;
import com.example.giveandtake.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ChatUsersRepository extends JpaRepository<ChatUsers, Long> {

    List<ChatUsers> findAllByUser(User user);

    @Override
    void deleteById(Long cid);
}
