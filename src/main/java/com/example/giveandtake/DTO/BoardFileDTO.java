package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.BoardFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardFileDTO {

    private String uuid;
    private String uploadPath;
    private String fileName;
    private Boolean fileType;
    private BoardDTO board;

//    public BoardFile toEntity(){
//        return BoardFile.builder()
//                .uuid(uuid)
//                .uploadPath(uploadPath)
//                .fileName(fileName)
//                .fileType(fileType)
//                .build();
//    }

    @Builder
    public BoardFileDTO(String uuid, String uploadPath, String fileName, Boolean fileType, BoardDTO board) {
        this.uuid = uuid;
        this.uploadPath = uploadPath;
        this.fileName = fileName;
        this.fileType = fileType;
        this.board = board;
    }
}
