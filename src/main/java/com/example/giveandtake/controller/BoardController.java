package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.Service.BoardService;
import com.example.giveandtake.common.Criteria;
import com.example.giveandtake.common.Pagination;
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

//    @GetMapping
//    public String list(Criteria cri, Model model){
//        logger.info("-----board list-----");
//
//        // 게시물 페이징
//        List<BoardDto> boardDtoList = boardService.getList(cri);
//        // 밑에 번호 페이징
//        Long total = boardService.getBoardCount();
//
//        model.addAttribute("boardList", boardDtoList);
//        model.addAttribute("pageMaker", pageMaker);
//
//        return "/board/list";
//    }

    @GetMapping
    public String list(Criteria cri, Model model){
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

    @GetMapping("/read/{no}")
    public String readGET(@PathVariable("no") Long bid, Model model){
        logger.info("-----board readGET-----");

        BoardDTO boardDto = boardService.getBoard(bid);

        model.addAttribute("boardDto", boardDto);

        return "/board/detail";
    }


    @GetMapping("/edit/{no}")
    public String modifyGET(@PathVariable("no") Long bid, Model model){
        logger.info("-----board modifyGET-----");

        BoardDTO boardDto = boardService.getBoard(bid);

        model.addAttribute("boardDto", boardDto);

        return "/board/modify";
    }

    @PostMapping("/edit/{no}")
    public String modifyPOST(BoardDTO dto){
        logger.info("-----board modifyPOST-----");

        boardService.update(dto);

        return "redirect:/board";
    }

    @PostMapping("/remove/{no}")
    public String removePOST(@PathVariable("no") Long bid){
        logger.info("-----board removePOST-----");

        boardService.delete(bid);

        return "redirect:/board";
    }

//    @GetMapping("/search")
//    public String searchGET(@RequestParam(value = "keyword") String keyword, @RequestParam(value = "page", defaultValue = "1") Integer pageNum, Model model){
//        logger.info("-----board searchGET");
//
//        if(keyword.equals("")) return "redirect:/board";
//
//        Pagination pageMaker = boardService.getPageMaker(pageNum);
//        List<BoardDto> boardDtoList = boardService.searchBoard(pageNum, keyword);
//
//        model.addAttribute("boardList", boardDtoList);
//        model.addAttribute("pageMaker", pageMaker);
//
//        return "/board/list";
//    }

}
