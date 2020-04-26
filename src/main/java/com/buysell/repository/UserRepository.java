package com.buysell.repository;

import com.buysell.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

      User findByEmail(String email);
      User findByUsername(String username);
      User findByNickname(String nickname);

      List<User> findByEmailAndName(String email, String name);
      Page<User> findAllByNicknameContaining(String nickName, Pageable pageable);
      Page<User> findAllByEmailContaining(String keyword, Pageable pageable);
}