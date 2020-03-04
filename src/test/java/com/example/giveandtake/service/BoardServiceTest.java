package com.example.giveandtake.service;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.BoardFileDTO;
import com.example.giveandtake.model.entity.BoardFile;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BoardServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(BoardServiceTest.class);

    @Autowired
    BoardService boardService;

    @Test
    public void registerTest(){
//        BoardDTO dto = new BoardDTO();
//        BoardFileDTO fileDTO = new BoardFileDTO();
//        List<BoardFileDTO> fileDTOList = new ArrayList<>();
//        fileDTO.setFileName("사진1");
//        fileDTO.setFileType(true);
//        fileDTO.setUploadPath("2020/03/01");
//        fileDTO.setUuid("30178876-1482-4b23-9e11-a96dcacd4d");
//        fileDTO.setBoardDTO(dto);
//        fileDTOList.add(fileDTO);
//
//        dto.setBtype("외식");
//        dto.setTitle("빕스");
//        dto.setContent("빕스 50000원");
//        dto.setWriter("dlwlrma");
//        dto.setPrice(45000);
//        dto.setBoardFileList(fileDTOList);
//        dto.toEntity();


//        boardService.register(dto);


    }
}
