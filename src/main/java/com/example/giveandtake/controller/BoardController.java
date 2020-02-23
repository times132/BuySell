package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.UserDTO;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

//        logger.info("-----getTotalElements----- : " + boardPage.getTotalElements());
//        logger.info("-----getTotalPages----- : " + boardPage.getTotalPages());
//        logger.info("-----getNumber----- : " + boardPage.getNumber());
//        logger.info("-----getSize----- : " + boardPage.getSize());
//        logger.info("-----get----- : " + boardPage.get());
//        logger.info("-----toList----- : " + boardPage.toList());
//        logger.info("-----getContent----- : " + boardPage.getContent());

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
//        UserDTO userList= userService.readUserByEmail(principal.getName()); //현재사용자정보 불러오기
//        model.addAttribute("userList",userList);
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

    @PostMapping("/modify")
    public String modifyPOST(@ModelAttribute SearchCriteria searchCri, BoardDTO dto){
        logger.info("-----board modifyPOST-----");

        boardService.update(dto);

        return "redirect:/board" + searchCri.makeSearchUrl(searchCri.getPage());
    }

    @PostMapping("/remove")
    public String removePOST(@ModelAttribute SearchCriteria searchCri, @RequestParam("bid") Long bid){
        logger.info("-----board removePOST-----");

        boardService.delete(bid);

        return "redirect:/board" + searchCri.makeSearchUrl(searchCri.getPage());
    }

}
