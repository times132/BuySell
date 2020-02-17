package com.example.giveandtake.Service;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.common.Criteria;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.repository.BoardRepository;
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
public class BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    private static final int listSize = 5; // 밑에 보이는 페이지 개수
    private static final int rangeSize = 4; // 한 페이지에 보이는 게시물 개수

    private BoardRepository boardRepository;

    // 게시물 등록
    public Long register(BoardDTO dto){
        return boardRepository.save(dto.toEntity()).getBid();
    }

    // 게시물 목록, 페이징, 검색
    public Page<Board> getList(Criteria cri){
        Pageable pageable = PageRequest.of(cri.getPage()-1, rangeSize, Sort.by(Sort.Direction.ASC, "createdDate"));

        Page<Board> page;
        logger.info("======getType()====== : " + cri.getType());

        if (cri.getType().equals("")){
            page = boardRepository.findAll(pageable);
        }else if (cri.getType().equals("TC")){
            page = boardRepository.findAllByTitleContainingOrContentContaining(cri.getKeyword(), cri.getKeyword(), pageable);
        }else{
            page = boardRepository.findAllByWriter(cri.getKeyword(), pageable);
        }

        return page;
    }

    // 게시물 상세 페이지 정보
    public BoardDTO getBoard(Long bid){
        Optional<Board> boardWrapper = boardRepository.findById(bid);
        Board board = boardWrapper.get();

        BoardDTO boardDto = BoardDTO.builder()
                .bid(board.getBid())
                .btype(board.getBtype())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .price(board.getPrice())
                .createdDate(board.getCreatedDate())
                .build();

        return boardDto;
    }

    // 게시물 업데이트
    public Long update(BoardDTO dto){
        return boardRepository.save(dto.toEntity()).getBid();
    }

    // 게시물 삭제
    public void delete(Long bid){
        boardRepository.deleteById(bid);
    }

    private BoardDTO convertModelToDto(Board board){
        return BoardDTO.builder()
                .bid(board.getBid())
                .btype(board.getBtype())
                .title(board.getTitle())
                .writer(board.getWriter())
                .createdDate(board.getCreatedDate())
                .build();
    }
}
