package com.buysell.domain.DTO;

import com.buysell.domain.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@NoArgsConstructor
public class BoardFileDTO {
    private static final Logger logger = LoggerFactory.getLogger(BoardFileDTO.class);
    private Long fid;
    private String uuid;
    private String uploadPath;
    private String fileName;
    private Boolean image;
    private Board board;

    @Builder
    public BoardFileDTO(Long fid, String uuid, String uploadPath, String fileName, Boolean image, Board board) {
        this.fid = fid;
        this.uuid = uuid;
        this.uploadPath = uploadPath;
        this.fileName = fileName;
        this.image = image;
        this.board = board;
    }
}
