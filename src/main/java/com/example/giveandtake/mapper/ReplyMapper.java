package com.example.giveandtake.mapper;

import com.example.giveandtake.DTO.ReplyDTO;
import com.example.giveandtake.model.entity.Reply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReplyMapper {

    ReplyDTO toDTO(Reply entity);

    Reply toEntity(ReplyDTO dto);
}
