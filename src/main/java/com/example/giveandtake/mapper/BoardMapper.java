package com.example.giveandtake.mapper;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.BoardFileDTO;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.BoardFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    BoardDTO toDTO(Board entity);
    List<BoardFileDTO> fileToDTOList(List<BoardFile> list);

    @Mapping(target = "boardFileList", ignore = true)
    Board toEntity(BoardDTO dto);

    BoardFile filetoEntity(BoardFileDTO dto);


}
