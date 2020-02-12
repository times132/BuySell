package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.BoardDto;
import com.example.giveandtake.Service.BoardService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    private BoardService boardService;

    @GetMapping
    public String list(Model model){
        logger.info("-----board list-----");

        List<BoardDto> boardList = boardService.getList();

        model.addAttribute("boardList", boardList);

        return "/board/list";
    }

    @GetMapping("/write")
    public String writeGET(){
        logger.info("-----board registerGET-----");

        return "/board/write";
    }

    @PostMapping("/write")
    public String writePOST(BoardDto dto){
        logger.info("-----board registerPOST-----");

        boardService.register(dto);

        return "redirect:/board";
    }

    @GetMapping("/{no}")
    public String readGET(@PathVariable("no") Long bid, Model model){
        logger.info("-----board readGET-----");

        BoardDto boardDto = boardService.getBoard(bid);

        model.addAttribute("boardDto", boardDto);

        return "/board/detail";
    }


    @GetMapping("/edit/{no}")
    public String modifyGET(@PathVariable("no") Long bid, Model model){
        logger.info("-----board modifyGET-----");

        BoardDto boardDto = boardService.getBoard(bid);

        model.addAttribute("boardDto", boardDto);

        return "/board/modify";
    }

    @PostMapping("/edit/{no}")
    public String modifyPOST(BoardDto dto){
        logger.info("-----board modifyPOST-----");

        boardService.update(dto);

        return "redirect:/board";
    }
}
