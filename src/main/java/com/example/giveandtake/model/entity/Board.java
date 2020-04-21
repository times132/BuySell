package com.example.giveandtake.model.entity;

import com.example.giveandtake.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "boards")
public class Board extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;
    private String category;
    private String title;
    private String content;
    private String writer;
    private Integer price;

    private Integer viewCnt;
    private Integer replyCnt;
    private Integer likeCnt;
    @ColumnDefault("0")
    private boolean sellCheck;

    @PrePersist
    protected void prePersist(){
        if (this.viewCnt == null) this.viewCnt = 0;
        if (this.replyCnt == null) this.replyCnt = 0;
        if (this.likeCnt == null) this.likeCnt = 0;
    }

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BoardFile> boardFileList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reply> replyList;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Like> likeList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"password", "boardList", "phone"})
    private User user;

    @Builder
    public Board(Long bid, String category, String title, String content, String writer, Integer price, Integer viewCnt, Integer replyCnt, Integer likeCnt, boolean sellCheck, List<BoardFile> boardFileList, User user, List<Like> likeList){
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
        this.boardFileList = boardFileList;
        this.likeList = likeList;
        this.user = user;
    }
}
