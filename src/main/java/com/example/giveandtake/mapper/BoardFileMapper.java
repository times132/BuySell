package com.example.giveandtake.mapper;

import com.example.giveandtake.DTO.BoardFileDTO;
import com.example.giveandtake.model.entity.BoardFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = BoardMapper.class)
public interface BoardFileMapper {

    // BoardFileDTO의 board안에 boardFileList는 제외함
    @Mapping(target = "board.boardFileList", ignore = true)
    BoardFileDTO toDTO(BoardFile entity);

    BoardFile toEntity(BoardFileDTO dto);

    @Named("boardfileDTOList")
    default List<BoardFileDTO> toboardFileDTOList(List<BoardFile> source){
        return source
                .stream()
                .map(this::toDTO)
                .peek(dto -> dto.setBoard(dto.getBoard()))
                .collect(Collectors.toList());
    }
}
