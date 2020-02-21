package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReplyDTO {

    private Long rid;
    private Long bid;

    private String reply;
    private String replyer;  //댓글 작성자 아이디
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Reply toEntity(){
        return Reply.builder()
                .rid(rid)
                .bid(bid)
                .reply(reply)
                .replyer(replyer)
                .build();
    }

    @Builder
    public ReplyDTO(Long rid, Long bid, String reply, String replyer, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.rid = rid;
        this.bid = bid;
        this.reply = reply;
        this.replyer = replyer;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
