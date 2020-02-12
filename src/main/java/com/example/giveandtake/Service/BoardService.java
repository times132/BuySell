package com.example.giveandtake.Service;

import com.example.giveandtake.DTO.BoardDto;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    private BoardRepository boardRepository;

    public Long register(BoardDto dto){
        return boardRepository.save(dto.toEntity()).getBid();
    }

    public List<BoardDto> getList(){
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (Board board : boardList){
            BoardDto boardDto = BoardDto.builder()
                    .bid(board.getBid())
                    .btype(board.getBtype())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .writer(board.getWriter())
                    .price(board.getPrice())
                    .createdDate(board.getCreatedDate())
                    .build();

            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }

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

    public Long update(BoardDto dto){
        return boardRepository.save(dto.toEntity()).getBid();
    }
}
