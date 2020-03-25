package com.example.giveandtake.repository;

import com.example.giveandtake.DTO.ReplyDTO;
import com.example.giveandtake.model.entity.Reply;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ReplyRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(ReplyRepositoryTest.class);

    @Autowired
    private ReplyRepository replyRepository;

//    @Test
//    public void saveReply(){
//        for (int i = 0; i < 10; i++){
//            ReplyDTO dto = ReplyDTO.builder()
//                    .bid(2L)
//                    .reply("더미 데이터" + i)
//                    .replyer("dlwlrma")
//                    .build();
//
//            replyRepository.save(dto.toEntity());
//        }
//
//    }

//    @Test
//    public void readReply(){
//        Optional<Reply> optional = replyRepository.findById(6L);
//        Reply reply = optional.get();
//        ReplyDTO dto = ReplyDTO.builder()
//                .rid(reply.getRid())
//                .bid(reply.getBid())
//                .reply(reply.getReply())
//                .replyer(reply.getReplyer())
//                .createdDate(reply.getCreatedDate())
//                .build();
//
//        logger.info(dto.toString());
//    }

//    @Test
//    public void deleteReply(){
//        replyRepository.deleteById(3L);
//    }
}
