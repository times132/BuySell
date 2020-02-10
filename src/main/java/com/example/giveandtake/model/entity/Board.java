package com.example.giveandtake.model.entity;

import com.example.giveandtake.model.audit.DateAudit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "boards")
public class Board extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;
    private String btype;
    private String title;
    private String content;
    private String nickname;
    private Integer price;

    @Builder
    public Board(String btype, String title, String content, String nickname, Integer price){
        this.btype =btype;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.price = price;
    }
}
