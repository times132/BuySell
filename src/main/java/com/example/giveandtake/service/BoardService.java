package com.example.giveandtake.service;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.common.SearchCriteria;
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

    private BoardRepository boardRepository;

    // 게시물 등록
    public Long register(BoardDTO dto){

        return boardRepository.save(dto.toEntity()).getBid();
    }

    // 게시물 목록, 페이징, 검색
    public Page<Board> getList(SearchCriteria SearchCri){
        Pageable pageable = PageRequest.of(SearchCri.getPage()-1, SearchCri.getPageSize(), Sort.by(Sort.Direction.ASC, "createdDate"));

        Page<Board> page;
        logger.info("======getType()====== : " + SearchCri.getType());

        if (SearchCri.getType().equals("")){
            page = boardRepository.findAll(pageable);
        }else if (SearchCri.getType().equals("TC")){
            page = boardRepository.findAllByTitleContainingOrContentContaining(SearchCri.getKeyword(), SearchCri.getKeyword(), pageable);
        }else{
            page = boardRepository.findAllByWriter(SearchCri.getKeyword(), pageable);
        }

        return page;
    }

    // 게시물 상세 페이지 정보
    public BoardDTO getBoard(Long bid){
        Optional<Board> boardWrapper = boardRepository.findById(bid);
        Board board = boardWrapper.get();

        return BoardDTO.builder()
                .bid(board.getBid())
                .btype(board.getBtype())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .price(board.getPrice())
                .createdDate(board.getCreatedDate())
                .build();
    }

    // 게시물 업데이트
    public Long update(BoardDTO dto){
        return boardRepository.save(dto.toEntity()).getBid();
    }

    // 게시물 삭제
    public void delete(Long bid){
        boardRepository.deleteById(bid);
    }
}
