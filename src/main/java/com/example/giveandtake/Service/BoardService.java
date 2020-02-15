package com.example.giveandtake.Service;

import com.example.giveandtake.DTO.BoardDto;
import com.example.giveandtake.common.Pagination;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    private static final int listSize = 5; // 밑에 보이는 페이지 개수
    private static final int rangeSize = 4; // 한 페이지에 보이는 게시물 개수

    private BoardRepository boardRepository;

    // 게시물 등록
    public Long register(BoardDto dto){
        return boardRepository.save(dto.toEntity()).getBid();
    }

    // 게시물 목록 출력
    public List<BoardDto> getList(Integer pageNum){
        // PageRequest.of() 첫번째 인자: sql의 limit / 두번째 인자: 몇개를 가져오나 / 세번째 인자: 정렬 방식
        Page<Board> page = boardRepository.findAll(PageRequest.of(pageNum-1, rangeSize, Sort.by(Sort.Direction.ASC, "createdDate")));

        // 반환된 타입이 Page라 getContent()로 리스트로 변환
        List<Board> boardList = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (Board board : boardList){
            boardDtoList.add(this.convertModelToDto(board));
        }

        return boardDtoList;
    }

    // 게시물 상세 페이지 정보
    public BoardDto getBoard(Long bid){
        Optional<Board> boardWrapper = boardRepository.findById(bid);
        Board board = boardWrapper.get();

        BoardDto boardDto = BoardDto.builder()
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
    public Long update(BoardDto dto){
        return boardRepository.save(dto.toEntity()).getBid();
    }

    // 게시물 삭제
    public void delete(Long bid){
        boardRepository.deleteById(bid);
    }

    // 게시물 검색
    public List<BoardDto> searchBoard(String keyword){
        List<Board> boardList = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if (boardList.isEmpty()) return boardDtoList;

        for (Board board : boardList){
            boardDtoList.add(this.convertModelToDto(board));
        }

        return boardDtoList;
    }

    // 총 게시물 개수
    public Long getBoardCount(){
        return boardRepository.count();
    }

    public Pagination getPageMaker(Integer curPage){
        double totalBoardCnt = Double.valueOf(getBoardCount());

        Pagination pageMaker = new Pagination(curPage, totalBoardCnt, listSize, rangeSize);

        logger.info("curpage: " + pageMaker.getCurPage());
        logger.info("startpage: " + pageMaker.getStartPage());
        logger.info("endpage: " + pageMaker.getEndPage());

        return pageMaker;
    }

    private BoardDto convertModelToDto(Board board){
        return BoardDto.builder()
                .bid(board.getBid())
                .btype(board.getBtype())
                .title(board.getTitle())
                .writer(board.getWriter())
                .createdDate(board.getCreatedDate())
                .build();
    }
}
