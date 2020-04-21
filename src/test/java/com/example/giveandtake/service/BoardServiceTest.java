package com.example.giveandtake.service;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.BoardFileDTO;
import com.example.giveandtake.common.CustomUserDetails;
import com.example.giveandtake.mapper.BoardMapper;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.BoardFile;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.repository.BoardRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    @WithUserDetails(value = "y4380")
    public void registerTest(){
        User user = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        for (int i=1; i<5; i++){
            Board board = Board.builder()
                    .category("뷰티")
                    .title("뷰티"+i)
                    .content("뷰티"+i)
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
