package com.buysell.service;

import com.buysell.domain.DTO.BoardDTO;
import com.buysell.domain.DTO.ReplyDTO;
import com.buysell.repository.BoardRepository;
import com.buysell.repository.ReplyRepository;
import com.buysell.common.Criteria;
import com.buysell.security.CustomUserDetails;
import com.buysell.mapper.BoardMapper;
import com.buysell.mapper.ReplyMapper;
import com.buysell.domain.entity.Board;
import com.buysell.domain.entity.Reply;
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

    public Long updateReply(ReplyDTO dto){
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
