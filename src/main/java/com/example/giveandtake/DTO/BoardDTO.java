package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime createdDate;

    public Board toEntity(){
        return Board.builder()
                .bid(bid)
                .btype(btype)
                .title(title)
                .content(content)
                .writer(writer)
                .price(price)
                .build();
    }

    @Builder
    public BoardDTO(Long bid, String btype, String title, String content, String writer, Integer price, LocalDateTime createdDate){
        this.bid = bid;
        this.btype = btype;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.price = price;
        this.createdDate = createdDate;
    }
}
