package com.example.giveandtake.service;

import com.example.giveandtake.DTO.ReplyDTO;
import com.example.giveandtake.common.Criteria;
import com.example.giveandtake.model.entity.Reply;
import com.example.giveandtake.repository.ReplyRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReplyService {

    private static final Logger logger = LoggerFactory.getLogger(ReplyService.class);

    private ReplyRepository replyRepository;

    public Long writeReply(ReplyDTO dto){
        return replyRepository.save(dto.toEntity()).getRid();
    }

    public Page<Reply> readReplyList(Long bid, Criteria cri){
        Pageable pageable = PageRequest.of(cri.getPage()-1, cri.getPageSize(), Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<Reply> page = replyRepository.findAllByBid(bid, pageable);
//        logger.info("댓글 수: " + page.getTotalElements());
//        List<ReplyDTO> replyDTOList = new ArrayList<>();
//
//        for (Reply reply : page.getContent()){
//            replyDTOList.add(convertEntityToDto(reply));
//        }

        return page;
    }

    public ReplyDTO readReply(Long rid){
        Optional<Reply> replyWapper = replyRepository.findById(rid);
        Reply reply = replyWapper.get();

        return convertEntityToDto(reply);
    }

    public Long updateReply(ReplyDTO replyDTO){
        return replyRepository.save(replyDTO.toEntity()).getRid();
    }

    public void deleteReply(Long rid){
        replyRepository.deleteById(rid);
    }

    private ReplyDTO convertEntityToDto(Reply reply){
        return ReplyDTO.builder()
                .rid(reply.getRid())
                .bid(reply.getBid())
                .reply(reply.getReply())
                .replyer(reply.getReplyer())
                .createdDate(reply.getCreatedDate())
                .updatedDate(reply.getUpdatedDate())
                .build();
    }
}
