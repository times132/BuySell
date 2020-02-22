package com.example.giveandtake.model.entity;

import com.example.giveandtake.model.audit.DateAudit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "replys")
public class Reply extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;
    @Column(updatable=false)
    private Long bid;

    private String reply;
    @Column(updatable=false)
    private String replyer;

    @Builder
    public Reply(Long rid, Long bid, String reply, String replyer){
        this.rid = rid;
        this.bid = bid;
        this.reply = reply;
        this.replyer = replyer;
    }
}
