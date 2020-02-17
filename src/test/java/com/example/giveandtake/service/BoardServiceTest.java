package com.example.giveandtake.service;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.Service.BoardService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BoardServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(BoardServiceTest.class);

    @Autowired
    BoardService boardService;

    @Test
    public void registerTest(){
        BoardDTO dto = new BoardDTO();
        dto.setBtype("외식");
        dto.setTitle("빕스");
        dto.setContent("빕스 50000원");
        dto.setWriter("dlwlrma");
        dto.setPrice(45000);
        dto.toEntity();

        for (int i=0; i<20; i++){
            boardService.register(dto);
        }

    }
}
