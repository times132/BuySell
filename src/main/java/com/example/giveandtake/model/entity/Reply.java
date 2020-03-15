package com.example.giveandtake.model.entity;

import com.example.giveandtake.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "replys")
public class Reply extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;
    @Column(updatable = false)
    private Long bid;

    private String reply;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"id", "replyList", "boardList", "password", "email", "profileImage", "phone", "roles"})
    private User user;

    @Builder
    public Reply(Long rid, Long bid, String reply, User user){
        this.rid = rid;
        this.bid = bid;
        this.reply = reply;
        this.user = user;
    }
}
