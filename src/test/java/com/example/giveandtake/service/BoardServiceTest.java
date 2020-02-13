package com.example.giveandtake.service;

import com.example.giveandtake.DTO.BoardDto;
import com.example.giveandtake.Service.BoardService;
import com.example.giveandtake.model.entity.Board;
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
        BoardDto dto = new BoardDto();
        dto.setBtype("커피");
        dto.setTitle("스타벅스 아메리카노");
        dto.setContent("아메리카노 기프티콘 팝니다");
        dto.setWriter("times132");
        dto.setPrice(3500);
        dto.toEntity();

        boardService.register(dto);
    }

    @Test
    public void searchTest(){
        List<BoardDto> boardDtoList = boardService.searchBoard("aa");

        assertEquals(2, boardDtoList.size());
    }
}
