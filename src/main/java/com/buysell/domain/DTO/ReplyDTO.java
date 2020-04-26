package com.buysell.domain.DTO;

import com.buysell.domain.entity.Board;
import com.buysell.domain.entity.User;
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