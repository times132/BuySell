package com.example.giveandtake.mapper;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.BoardFileDTO;
import com.example.giveandtake.DTO.LikeDTO;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.BoardFile;
import com.example.giveandtake.model.entity.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    // 게시물 저장, 수정할 때
    @Mapping(target = "boardFileList", ignore = true)
    Board toEntity(BoardDTO dto);

    // 게시물 불러올 때
//    @Mapping(target = "boardFileList", ignore = true)
    BoardDTO toDTO(Board entity);


    BoardFile fileToEntity(BoardFileDTO dto);

    @Mapping(target = "board", ignore = true)
    BoardFileDTO fileToDTO(BoardFile entity);
    List<BoardFileDTO> fileToDTOList(List<BoardFile> list);

    Like likeToEntity(LikeDTO likeDTO);
}
