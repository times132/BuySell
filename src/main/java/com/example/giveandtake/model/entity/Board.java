package com.example.giveandtake.model.entity;

import com.example.giveandtake.model.audit.DateAudit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "boards")
public class Board extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;
    private String btype;
    private String title;
    private String content;
    private String writer;
    private Integer price;

    @Builder
    public Board(Long bid, String btype, String title, String content, String writer, Integer price){
        this.bid = bid;
        this.btype = btype;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.price = price;
    }
}
