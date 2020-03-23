package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReplyDTO {

    private Long rid;
    private Long bid;
    private String reply;
    @JsonIgnoreProperties({"id", "replyList", "boardList", "password", "email", "phone", "profileImage", "roles"})
    private User user;
    private Board board;
    private LocalDateTime createdDate;
}