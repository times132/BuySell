package com.example.giveandtake.service;

import com.example.giveandtake.DTO.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReplyServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ReplyServiceTest.class);

    @Autowired
    private ReplyService replyService;

//    @Test
//    public void replyUpdate(){
//        ReplyDTO replyDTO = ReplyDTO.builder()
//                .rid(24L)
//                .bid(2L)
//                .reply("댓글 업데이트")
//                .replyer("iu")
//                .build();
//
//        replyService.updateReply(replyDTO);
//    }
}
