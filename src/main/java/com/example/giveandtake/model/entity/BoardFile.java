package com.example.giveandtake.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "files")
public class BoardFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid;
    private String uuid;
    private String uploadPath;
    private String fileName;
    private Boolean image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_bid")
    private Board board;

    @Builder
    public BoardFile(Long fid, String uuid, String uploadPath, String fileName, Boolean image, Board board){
        this.fid = fid;
        this.uuid = uuid;
        this.uploadPath = uploadPath;
        this.fileName = fileName;
        this.image = image;
        this.board = board;
    }
}
