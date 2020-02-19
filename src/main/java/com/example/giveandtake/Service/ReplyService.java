package com.example.giveandtake.Service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReplyService {

    private static final Logger logger = LoggerFactory.getLogger(ReplyService.class);
    private static final int rangeSize = 5; // 한 페이지에 보이는 게시물 개수

    private ReplyRepository replyRepository;

    public Long writeReply(ReplyDTO dto){
        return replyRepository.save(dto.toEntity()).getRid();
    }

    public List<ReplyDTO> readReplyList(Long bid, Criteria cri){
        Pageable pageable = PageRequest.of(cri.getPage()-1, rangeSize, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<Reply> page = replyRepository.findAllByBid(bid, pageable);

        List<ReplyDTO> replyDTOList = new ArrayList<>();

        for (Reply reply : page.getContent()){
            replyDTOList.add(convertEntityToDto(reply));
        }

        return replyDTOList;
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
