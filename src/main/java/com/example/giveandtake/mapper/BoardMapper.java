package com.example.giveandtake.mapper;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.BoardFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BoardFileMapper.class)
public interface BoardMapper {

    @Mapping(target = "boardFileList", source = "boardFileList", qualifiedByName = "boardfileDTOList")
    BoardDTO toDTO(Board entity);

    Board toEntity(BoardDTO dto);
}
