package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.Service.BoardService;
import com.example.giveandtake.common.Criteria;
import com.example.giveandtake.common.Pagination;
import com.example.giveandtake.common.SearchCriteria;
import com.example.giveandtake.model.entity.Board;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    private BoardService boardService;

    @GetMapping
    public String list(SearchCriteria cri, Model model){
        logger.info("-----board list-----");

        Page<Board> boardPage =  boardService.getList(cri);

        logger.info("-----getTotalElements----- : " + boardPage.getTotalElements());
        logger.info("-----getTotalPages----- : " + boardPage.getTotalPages());
        logger.info("-----getNumber----- : " + boardPage.getNumber());
        logger.info("-----getSize----- : " + boardPage.getSize());
        logger.info("-----get----- : " + boardPage.get());
        logger.info("-----toList----- : " + boardPage.toList());
        logger.info("-----getContent----- : " + boardPage.getContent());

        model.addAttribute("boardList", boardPage.getContent());
        model.addAttribute("pageMaker", Pagination.builder()
                            .cri(cri)
                            .total(boardPage.getTotalElements())
                            .rangeSize(boardPage.getSize())
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
    public String writePOST(BoardDTO dto){
        logger.info("-----board registerPOST-----");

        boardService.register(dto);

        return "redirect:/board";
    }

    @GetMapping({"/read", "/modify"})
    public void readGET(@RequestParam("bid") Long bid, @ModelAttribute("cri") SearchCriteria cri, Model model){
        logger.info("-----board readGET-----");

        BoardDTO boardDto = boardService.getBoard(bid);

        model.addAttribute("boardDto", boardDto);
    }

    @PostMapping("/modify")
    public String modifyPOST(@ModelAttribute SearchCriteria cri, BoardDTO dto){
        logger.info("-----board modifyPOST-----");

        boardService.update(dto);

        return "redirect:/board" + cri.makeSearchUrl(cri.getPage());
    }

    @PostMapping("/remove")
    public String removePOST(@ModelAttribute SearchCriteria cri, @RequestParam("bid") Long bid){
        logger.info("-----board removePOST-----");

        boardService.delete(bid);

        return "redirect:/board" + cri.makeSearchUrl(cri.getPage());
    }

}
