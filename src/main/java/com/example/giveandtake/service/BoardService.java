package com.example.giveandtake.service;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.BoardFileDTO;
import com.example.giveandtake.common.CustomUserDetails;
import com.example.giveandtake.common.SearchCriteria;
import com.example.giveandtake.mapper.BoardMapper;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.BoardFile;
import com.example.giveandtake.repository.BoardFileRepository;
import com.example.giveandtake.repository.BoardRepository;
import javassist.compiler.Parser;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    private BoardRepository boardRepository;
    private BoardFileRepository boardFileRepository;
    private BoardMapper boardMapper;

    // 게시물 등록
    @Transactional
    public void register(BoardDTO dto, CustomUserDetails userDetails){
        dto.setUser(userDetails.getUser());
        Board board = boardRepository.save(boardMapper.toEntity(dto));
        // 첨부 파일 저장
        for (BoardFileDTO fileDTO : dto.getBoardFileList()){
            fileDTO.setBoard(board);
            boardFileRepository.save(boardMapper.fileToEntity(fileDTO));
        }
    }

    // 게시물 목록, 페이징, 검색
    public Page<Board> getList(SearchCriteria SearchCri){
        Pageable pageable = PageRequest.of(SearchCri.getPage()-1, SearchCri.getPageSize(), Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<Board> page;

        if (SearchCri.getType().equals("")){
            page = boardRepository.findAll(pageable);
        }else if (SearchCri.getType().equals("TC")){ // 제목+내용으로 검색
            page = boardRepository.findAllByTitleContainingOrContentContaining(SearchCri.getKeyword(), SearchCri.getKeyword(), pageable);
        }else if (SearchCri.getType().equals("W")){ // 작성자로 검색
            page = boardRepository.findAllByWriter(SearchCri.getKeyword(), pageable);
        }else{ // id로 검색
            page = boardRepository.findAllByUserId(Long.parseLong(SearchCri.getKeyword()), pageable);
        }

        return page;
    }

    // 게시물 상세 페이지 정보
    @Transactional
    public BoardDTO getBoard(Long bid){
        logger.info("######get board#######");
        Optional<Board> boardWrapper = boardRepository.findById(bid);
        Board board = boardWrapper.get();

        return boardMapper.toDTO(board);
    }

    // 게시물 업데이트
    @Transactional
    public void update(BoardDTO dto, CustomUserDetails userDetails){
        dto.setUser(userDetails.getUser());
        Board board = boardRepository.save(boardMapper.toEntity(dto));

        // 기존 게시물에 있던 사진 uuid 저장
        Set<String> uuidList = boardFileRepository.findAllByBoardBid(dto.getBid())
                .stream()
                .map(BoardFile::getUuid)
                .collect(Collectors.toSet());

        for (BoardFileDTO fileDTO : dto.getBoardFileList()){
            uuidList.remove(fileDTO.getUuid());
            fileDTO.setBoard(board);
            boardFileRepository.save(boardMapper.fileToEntity(fileDTO));
        }

        for (String uuid : uuidList){
            boardFileRepository.deleteByUuid(uuid);
        }
    }

    // 게시물 삭제
    public void delete(Long bid){
        boardRepository.deleteById(bid);
    }

    public List<BoardFileDTO> readFile(Long bid){
        return boardMapper.fileToDTOList(boardFileRepository.findAllByBoardBid(bid));
    }

    @Transactional
    public void addViewCount(Long bid){
        Optional<Board> boardWrapper = boardRepository.findById(bid);
        Board board = boardWrapper.get();
        BoardDTO boardDTO = boardMapper.toDTO(board);
        boardDTO.setViewCnt(boardDTO.getViewCnt()+1);

        boardRepository.save(boardMapper.toEntity(boardDTO));
    }
}
