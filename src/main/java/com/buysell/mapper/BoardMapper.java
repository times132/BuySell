package com.buysell.mapper;

import com.buysell.domain.DTO.BoardDTO;
import com.buysell.domain.DTO.BoardFileDTO;
import com.buysell.domain.DTO.LikeDTO;
import com.buysell.domain.entity.Board;
import com.buysell.domain.entity.BoardFile;
import com.buysell.domain.entity.Like;
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
