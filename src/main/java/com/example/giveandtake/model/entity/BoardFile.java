package com.example.giveandtake.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "files")
public class BoardFile {

    @Id
    private String uuid;
    private String uploadPath;
    private String fileName;
    private Boolean fileType;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    @Builder
    public BoardFile(String uuid, String uploadPath, String fileName, Boolean fileType, Board board){
        this.uuid = uuid;
        this.uploadPath = uploadPath;
        this.fileName = fileName;
        this.fileType = fileType;
        this.board = board;
    }
}
