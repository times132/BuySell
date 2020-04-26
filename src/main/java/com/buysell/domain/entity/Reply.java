package com.buysell.domain.entity;

import com.buysell.domain.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "replys")
public class Reply extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;
    private String reply;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"replyList", "boardList", "password", "email", "phone", "roles", "provider", "username", "name", "createdDate", "updatedDate", "activation"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_bid")
    @JsonIgnoreProperties({"replyList", "boardFileList", "user"})
    private Board board;

    @Builder
    public Reply(Long rid, String reply, User user, Board board){
        this.rid = rid;
        this.reply = reply;
        this.user = user;
        this.board = board;
    }
}
