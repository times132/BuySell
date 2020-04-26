package com.buysell.mapper;

import com.buysell.domain.DTO.ReplyDTO;
import com.buysell.domain.entity.Reply;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReplyMapper {

    ReplyDTO toDTO(Reply entity);

    Reply toEntity(ReplyDTO dto);
}
