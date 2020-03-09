package com.example.giveandtake.model.entity;

import com.example.giveandtake.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private Integer viewCnt;
    private Integer replyCnt;

    @PrePersist
    protected void prePersist(){
        if (this.viewCnt == null) this.viewCnt = 0;
        if (this.replyCnt == null) this.replyCnt = 0;
    }

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BoardFile> boardFileList = new ArrayList<>();

    @Builder
    public Board(Long bid, String btype, String title, String content, String writer, Integer price, Integer viewCnt, Integer replyCnt, List<BoardFile> boardFileList){
        this.bid = bid;
        this.btype = btype;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.price = price;
        this.viewCnt = viewCnt;
        this.replyCnt = replyCnt;
        this.boardFileList = boardFileList;
    }
}
