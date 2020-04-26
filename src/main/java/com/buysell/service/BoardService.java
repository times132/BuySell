package com.buysell.service;

import com.buysell.domain.DTO.BoardDTO;
import com.buysell.domain.DTO.BoardFileDTO;
import com.buysell.domain.DTO.LikeDTO;
import com.buysell.repository.BoardFileRepository;
import com.buysell.repository.BoardRepository;
import com.buysell.repository.LikeRepository;
import com.buysell.security.CustomUserDetails;
import com.buysell.common.SearchCriteria;
import com.buysell.mapper.BoardMapper;
import com.buysell.domain.entity.Board;
import com.buysell.domain.entity.BoardFile;
import com.buysell.domain.entity.Like;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private LikeRepository likeRepository;

    // 게시물 등록
    @Transactional
    public void write(BoardDTO dto, CustomUserDetails userDetails){
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
        } else if (SearchCri.getType().equals("C")) { // 대분류로 검색
            page = null;
        } else if (SearchCri.getType().equals("I")) { // 소분류로 검색
            page = boardRepository.findAllByCategory(SearchCri.getKeyword(), pageable);
        } else { // id로 검색
            page = boardRepository.findAllByUserId(Long.parseLong(SearchCri.getKeyword()), pageable);
        }

        return page;
    }

    // 게시물 상세 페이지 정보
    @Transactional
    public BoardDTO getBoardDetail(Long bid){
        Optional<Board> boardWrapper = boardRepository.findById(bid);
        Board board = boardWrapper.get();

        return boardMapper.toDTO(board);
    }

    // 게시물 업데이트
    @Transactional
    public void update(BoardDTO dto, CustomUserDetails userDetails){
        Board board = boardRepository.findById(dto.getBid()).get();
        BoardDTO boardDTO = boardMapper.toDTO(board);
        boardDTO.setCategory(dto.getCategory());
        boardDTO.setTitle(dto.getTitle());
        boardDTO.setContent(dto.getContent());
        boardDTO.setPrice(dto.getPrice());

        board = boardRepository.save(boardMapper.toEntity(boardDTO));

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

    public void sell(Long bid){
        Board board = boardRepository.findById(bid).get();
        BoardDTO boardDTO = boardMapper.toDTO(board);
        boardDTO.setSellCheck(true);
        boardRepository.save(boardMapper.toEntity(boardDTO));
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


    public boolean checkLike(Long bid, CustomUserDetails userDetails) {
        System.out.println("CHECK");
        Optional<Like> like = Optional.ofNullable(likeRepository.findByUserIdAndBoardBid(userDetails.getUser().getId(),bid));

        if(like.isPresent()){
            return true;
        }
        return false;
    }
    @Transactional
    public int addLike(Long bid, CustomUserDetails userDetails) {
        System.out.println("좋아요");
        Optional<Board> boardWrapper = boardRepository.findById(bid);
        Board board = boardWrapper.get();

        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setBoard(board);
        likeDTO.setUser(userDetails.getUser());
        Like like = likeRepository.save(boardMapper.likeToEntity(likeDTO));

        BoardDTO boardDTO = boardMapper.toDTO(board);
        boardDTO.setLikeCnt(boardDTO.getLikeCnt()+1);
        return boardRepository.save(boardMapper.toEntity(boardDTO)).getLikeCnt();
    }

    public int deleteLike(Long bid, CustomUserDetails userDetails) {

        Long id = likeRepository.findByUserIdAndBoardBid(userDetails.getUser().getId(),bid).getId();
        likeRepository.deleteById(id);

        Optional<Board> boardWrapper = boardRepository.findById(bid);
        Board board = boardWrapper.get();
        BoardDTO boardDTO = boardMapper.toDTO(board);
        boardDTO.setLikeCnt(boardDTO.getLikeCnt()-1);

        return  boardRepository.save(boardMapper.toEntity(boardDTO)).getLikeCnt();
    }
}
