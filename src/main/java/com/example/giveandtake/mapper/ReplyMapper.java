package com.example.giveandtake.mapper;

import com.example.giveandtake.DTO.ReplyDTO;
import com.example.giveandtake.model.entity.Reply;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReplyMapper {

    ReplyDTO toDTO(Reply entity);
}
