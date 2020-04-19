package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.ChatUsers;
import com.example.giveandtake.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



      User findByEmail(String email);
      User findByUserName(String username);
      User findByNickName(String nickname);

      List<User> findByEmailAndName(String email, String name);
      Page<User> findAllByNickNameContaining(String nickName, Pageable pageable);
      Page<User> findAllByEmailContaining(String keyword, Pageable pageable);
}