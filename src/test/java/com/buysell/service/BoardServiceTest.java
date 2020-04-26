package com.buysell.service;

import com.buysell.repository.BoardRepository;
import com.buysell.security.CustomUserDetails;
import com.buysell.domain.entity.Board;
import com.buysell.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BoardServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(BoardServiceTest.class);

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    UserService userService;

    @Test
    @WithUserDetails(value = "yoo4380")
    public void registerTest(){
        User user = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        for (int i=1; i<5; i++){
            Board board = Board.builder()
                    .category("애완")
                    .title("뽀삐팔아요 분양하세요"+i)
                    .content("뽀삐"+i)
                    .price(2146+i*578)
                    .replyCnt(0)
                    .viewCnt(0)
                    .writer(user.getNickname())
                    .user(user)
                    .build();

            boardRepository.save(board);
        }
//
//
//
//
//
//        boardService.register(dto);


    }
}
