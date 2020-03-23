package com.example.giveandtake.service;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.ReplyDTO;
import com.example.giveandtake.common.Criteria;
import com.example.giveandtake.common.CustomUserDetails;
import com.example.giveandtake.mapper.BoardMapper;
import com.example.giveandtake.mapper.ReplyMapper;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.Reply;
import com.example.giveandtake.repository.BoardRepository;
import com.example.giveandtake.repository.ReplyRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReplyService {

    private static final Logger logger = LoggerFactory.getLogger(ReplyService.class);

    private ReplyRepository replyRepository;
    private BoardRepository boardRepository;
    private BoardMapper boardMapper;
    private ReplyMapper replyMapper;

    @Transactional
    public Long writeReply(ReplyDTO dto, CustomUserDetails userDetails){
        Optional<Board> boardWapper = boardRepository.findById(dto.getBid()); // board 영속화
        Board board = boardWapper.get();

        // board 댓글 수 증가
        BoardDTO boarddto = boardMapper.toDTO(board);
        boarddto.setUser(board.getUser());
        boarddto.setReplyCnt(boarddto.getReplyCnt()+1);
        boardRepository.save(boardMapper.toEntity(boarddto));

        // 댓글에 부모 설정
        dto.setBoard(board);
        dto.setUser(userDetails.getUser());

        return replyRepository.save(replyMapper.toEntity(dto)).getRid();
    }

    public Page<Reply> readReplyList(Long bid, Criteria cri){
        Pageable pageable = PageRequest.of(cri.getPage()-1, cri.getPageSize(), Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<Reply> page = replyRepository.findAllByBoardBid(bid, pageable);

        return page;
    }

    public ReplyDTO readReply(Long rid){
        Optional<Reply> replyWapper = replyRepository.findById(rid);
        Reply reply = replyWapper.get();

        return replyMapper.toDTO(reply);
    }

    public Long updateReply(ReplyDTO dto, CustomUserDetails userDetails){
        Reply reply = replyRepository.findById(dto.getRid()).get();
        ReplyDTO modifyReply = replyMapper.toDTO(reply);
        modifyReply.setReply(dto.getReply());

        return replyRepository.save(replyMapper.toEntity(modifyReply)).getRid();
    }

    @Transactional
    public void deleteReply(Long rid){
        Long bid = replyRepository.findBidByRid(rid);
        Optional<Board> boardWapper = boardRepository.findById(bid);
        BoardDTO boarddto = boardMapper.toDTO(boardWapper.get());
        boarddto.setReplyCnt(boarddto.getReplyCnt()-1);
        boardRepository.save(boardMapper.toEntity(boarddto));

        replyRepository.deleteById(rid);
    }

    public Long countReply(Long bid){
        return replyRepository.countByBoardBid(bid);
    }
}
