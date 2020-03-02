package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.BoardFile;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {

    private Long bid;
    private String btype;
    private String title;
    private String content;
    private String writer;
    private Integer price;
    private Integer replyCnt;
    private LocalDateTime createdDate;
    private List<BoardFileDTO> boardFileList = new ArrayList<>();

//    public Board toEntity(){
//        return Board.builder()
//                .btype(btype)
//                .title(title)
//                .content(content)
//                .writer(writer)
//                .price(price)
//                .replyCnt(replyCnt)
//                .boardFileList(boardFileList)
//                .build();
//    }

    @Builder
    public BoardDTO(Long bid, String btype, String title, String content, String writer, Integer price, Integer replyCnt, LocalDateTime createdDate, List<BoardFileDTO> boardFileList){
        this.bid = bid;
        this.btype = btype;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.price = price;
        this.replyCnt = replyCnt;
        this.createdDate = createdDate;
        this.boardFileList = boardFileList;
    }
}
