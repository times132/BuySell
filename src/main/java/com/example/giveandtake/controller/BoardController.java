package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.BoardFileDTO;
import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.model.entity.BoardFile;
import com.example.giveandtake.service.BoardService;
import com.example.giveandtake.common.Pagination;
import com.example.giveandtake.common.SearchCriteria;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.repository.UserRepository;
import com.example.giveandtake.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    private BoardService boardService;
    private UserService userService;


    @GetMapping
    public String list(SearchCriteria searchCri, Model model){
        logger.info("-----board list-----");

        Page<Board> boardPage =  boardService.getList(searchCri);

        model.addAttribute("boardList", boardPage.getContent());
        model.addAttribute("pageMaker", Pagination.builder()
                            .cri(searchCri)
                            .total(boardPage.getTotalElements())
                            .realEndPage(boardPage.getTotalPages())
                            .listSize(5)
                            .build());

        return "/board/list";
    }

    @GetMapping("/write")
    public String writeGET(){
        logger.info("-----board registerGET-----");

        return "/board/write";
    }

    @PostMapping("/write")
    public String writePOST(BoardDTO boardDTO){
        logger.info("-----board registerPOST-----");

        boardService.register(boardDTO);

        return "redirect:/board";
    }

    @GetMapping({"/read", "/modify"})
    public void readGET(@RequestParam("bid") Long bid, @ModelAttribute("cri") SearchCriteria cri, Model model){
        logger.info("-----board readGET-----");

        BoardDTO boardDto = boardService.getBoard(bid);

        model.addAttribute("boardDto", boardDto);
    }

    @PreAuthorize("principal.username == #dto.writer")
    @PostMapping("/modify")
    public String modifyPOST(@ModelAttribute SearchCriteria searchCri, BoardDTO dto){
        logger.info("-----board modifyPOST-----");

        boardService.update(dto);

        return "redirect:/board" + searchCri.makeSearchUrl(searchCri.getPage());
    }

    @PreAuthorize("principal.username == #writer")
    @PostMapping("/remove")
    public String removePOST(@ModelAttribute SearchCriteria searchCri, @RequestParam("bid") Long bid, String writer){
        logger.info("-----board removePOST-----");
        logger.info("########WRITER : " + writer);
        boardService.delete(bid);

        return "redirect:/board" + searchCri.makeSearchUrl(searchCri.getPage());
    }

    @GetMapping(value = "/getFileList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<BoardFileDTO>> fileListGET(Long bid){

        return new ResponseEntity<>(boardService.readFile(bid), HttpStatus.OK);
    }
}
