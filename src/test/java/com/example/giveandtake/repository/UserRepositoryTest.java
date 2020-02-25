package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    UserRepository userRepository;

    @Test
    public void createUser(){
        userRepository.save(User.builder()
                .username("yooseunga")
                .name("sunny")
                .password("1234")
                .phone("010-1234-5678")
                .email("y4380@naver.com")
                .build());

        List<User> usersList = userRepository.findAll();

        User users = usersList.get(0);
        assertThat(users.getName(), is("times132"));
        assertThat(users.getEmail(), is("times132@naver.com"));
    }

    @Test
    public void deleteUser(){
        Optional<User> user = userRepository.findById(21L);
        assertTrue(user.isPresent()); // isPresent() 저장된 값이 null인지 아닌지 확인
        user.ifPresent(selectUser->{ // ifPresent() 값이 존재할 때만 실행 됨
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(21L);

        assertFalse(deleteUser.isPresent()); // false
    }

    @Test
    public void readUser(){
        Optional<User> user = userRepository.findById(10L);


        user.ifPresent(selectUser ->{
            System.out.println("user: "+selectUser);
        });
    }
//id는 모델에서 long으로 선언 되어 있으므로 L을 붙여 id가 1번인 데이터를 찾는다.
//데이터를 찾아 user객체에 담아주고 출력한다.


    @Test
    public void AuditTest(){
        LocalDateTime now = LocalDateTime.now();
        userRepository.save(User.builder()
                .username("Park")
                .name("times132")
                .password("123456")
                .phone("010-1234-5678")
                .email("times132@naver.com")
                .build());
    }
}