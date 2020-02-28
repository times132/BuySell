package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.BoardFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardFileDTO {

    private String uuid;
    private String uploadPath;
    private String fileName;
    private Boolean fileType;
    private BoardDTO boardDTO;

    public BoardFile toEntity(){
        return BoardFile.builder()
                .uuid(uuid)
                .uploadPath(uploadPath)
                .fileName(fileName)
                .fileType(fileType)
                .board(convertDtoToEntity(boardDTO))
                .build();
    }

    private Board convertDtoToEntity(BoardDTO dto){
        return dto.toEntity();
    }
}
