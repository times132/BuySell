package com.buysell.domain.DTO;

import com.buysell.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {

    private Long bid;
    private String category;
    private String title;
    private String content;
    private String writer;
    private Integer price;
    private Integer viewCnt;
    private Integer replyCnt;
    private Integer likeCnt;
    private boolean sellCheck;
    private LocalDateTime createdDate;
    private List<BoardFileDTO> boardFileList = new ArrayList<>();
    @JsonIgnore
    private User user;

    @Builder
    public BoardDTO(Long bid, String category, String title, String content, String writer, Integer price, Integer viewCnt, Integer replyCnt, Integer likeCnt, boolean sellCheck, LocalDateTime createdDate, List<BoardFileDTO> boardFileList, User user){
        this.bid = bid;
        this.category = category;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.price = price;
        this.viewCnt = viewCnt;
        this.replyCnt = replyCnt;
        this.likeCnt = likeCnt;
        this.sellCheck = sellCheck;
        this.createdDate = createdDate;
        this.boardFileList = boardFileList;
        this.user = user;
    }
}
