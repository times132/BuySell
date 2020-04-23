package com.example.giveandtake.controller;

import com.ctc.wstx.util.StringUtil;
import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.BoardFileDTO;

import com.example.giveandtake.common.CustomUserDetails;
import com.example.giveandtake.model.entity.Category;
import com.example.giveandtake.model.entity.CategoryItem;
import com.example.giveandtake.service.BoardService;
import com.example.giveandtake.common.Pagination;
import com.example.giveandtake.common.SearchCriteria;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.service.CategoryService;
import com.example.giveandtake.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.Attribute;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.Principal;
import java.security.acl.LastOwnerException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    private BoardService boardService;
    private CategoryService categoryService;

    @GetMapping
    public String boardListGET(SearchCriteria searchCri, Model model){


        searchCri.setPageSize(10); // 한 화면에 게시물 10개씩 표시
        Page<Board> boardPage =  boardService.getList(searchCri);
        logger.info("-----board list-----"+boardPage.getContent() );
        model.addAttribute("boardList", boardPage.getContent());
        model.addAttribute("pageMaker", Pagination.builder()
                            .cri(searchCri)
                            .total(boardPage.getTotalElements())
                            .realEndPage(boardPage.getTotalPages())
                            .listSize(5) // 페이징 5로 설정
                            .build());

        return "/board/list";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/write")
    public String writeGET(Model model){
        logger.info("-----board registerGET-----");
        List<Category> category =  categoryService.getCategory();
        model.addAttribute("category", category);
        return "/board/write";
    }

    @PostMapping("/write")
    public String writePOST(BoardDTO boardDTO, @AuthenticationPrincipal CustomUserDetails userDetails){
        logger.info("-----board registerPOST-----");

        boardService.write(boardDTO, userDetails);

        return "redirect:/board";
    }

    @GetMapping("/read")
    public String readGET(HttpServletRequest request, HttpServletResponse response, @RequestParam("bid") Long bid, @ModelAttribute("cri") SearchCriteria cri, Model model){
        logger.info("-----board readGET-----");

        BoardDTO boardDto = boardService.getBoardDetail(bid);
        model.addAttribute("boardDto", boardDto);

        // 쿠키로 조회수 조작 방지
        // 쿠키 불러오기
        Cookie[] cookies = request.getCookies();
        Map mapCookie = new HashMap();

        // 쿠키가 있으면 map에 저장
        if (request.getCookies() != null){
            for (int i = 0; i < cookies.length; i++){
                Cookie obj = cookies[i];
                mapCookie.put(obj.getName(), obj.getValue());
            }
        }

        // 쿠키중 'view_count'만 조회, 새로운 쿠키 생성
        String cookie_view_count = (String) mapCookie.get("view_count");
        String new_cookie = "|" + bid;

        // 'view_count'에 현재 게시물 쿠키가 없으면 추가 후 조회수 증가
        if (StringUtils.indexOfIgnoreCase(cookie_view_count, new_cookie) == -1){
            Cookie cookie = new Cookie("view_count", cookie_view_count + new_cookie);
            cookie.setMaxAge(60*3);
            response.addCookie(cookie);

            boardService.addViewCount(bid);
        }

        return "/board/read";
    }

    @GetMapping("/modify")
    public void modifyGET(@RequestParam("bid") Long bid, @ModelAttribute("cri") SearchCriteria cri, Model model){
        logger.info("-----board modifyGET-----");

        BoardDTO boardDto = boardService.getBoardDetail(bid);
        Category myCategory =  categoryService.getCateItems(boardDto.getCategory());
        List<Category> category =  categoryService.getCategory();
        model.addAttribute("category", category);
        model.addAttribute("myCategory", myCategory);
        model.addAttribute("boardDto", boardDto);
    }


    @PreAuthorize("principal.user.nickname == #dto.writer")
    @PostMapping("/modify")
    public String modifyPOST(@ModelAttribute SearchCriteria searchCri, BoardDTO dto, @AuthenticationPrincipal CustomUserDetails userDetails){
        logger.info("-----board modifyPOST-----");

        boardService.update(dto, userDetails);

        return "redirect:/board" + searchCri.makeSearchUrl(searchCri.getPage());
    }

    @PreAuthorize("principal.user.nickname == #writer")
    @PostMapping("/remove")
    public String removePOST(@ModelAttribute SearchCriteria searchCri, @RequestParam("bid") Long bid, String writer){
        logger.info("-----board removePOST-----");

        boardService.delete(bid);

        return "redirect:/board" + searchCri.makeSearchUrl(searchCri.getPage());
    }

    @PreAuthorize("principal.user.nickname == #writer")
    @PostMapping("sell")
    public String sellPOST(@RequestParam("bid") Long bid, String writer){
        logger.info("-----board sellPOST-----");
        boardService.sell(bid);
        return "redirect:/board";
    }


    @GetMapping(value = "/getFileList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<BoardFileDTO>> fileListGET(Long bid){

        return new ResponseEntity<>(boardService.readFile(bid), HttpStatus.OK);
    }




    //좋아요 여부 검사
    @GetMapping(value = "/like/{bid}")
    @ResponseBody
    public ResponseEntity<Boolean> checkLike(@PathVariable("bid") Long bid, @AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println("########BID"+ bid);
        boolean check = boardService.checkLike(bid, userDetails);
        return new ResponseEntity<>(check, HttpStatus.OK);
    }

    @PostMapping(value = "/like/{bid}")
    @ResponseBody
    public int likePOST(@PathVariable("bid") Long bid, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return  boardService.addLike(bid, userDetails);
    }

    @DeleteMapping(value = "/like/{bid}")
    @ResponseBody
    public int likeDELETE(@PathVariable("bid") Long bid, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return boardService.deleteLike(bid, userDetails);
    }


    //카테고리
    @GetMapping(value = "/category/{id}")
    @ResponseBody
    public ResponseEntity<List> CategoryItemsGET(@PathVariable("id") Long id) {
        System.out.println("########Category ID"+ id);
        List<CategoryItem> categoryItems = categoryService.getCategoryItems(id);
        return new ResponseEntity<>(categoryItems, HttpStatus.OK);
    }

    @Cacheable(value = "home")
    @GetMapping(value = "/item/{itemname}")
    @ResponseBody
    public ResponseEntity<List<Board>> ItemListGET(@PathVariable("itemname") String itemName){
        SearchCriteria searchCriteria = new SearchCriteria();
        if (!itemName.equals("whole")) searchCriteria.setType("I");
        searchCriteria.setPageSize(10);
        searchCriteria.setKeyword(itemName);
        List<Board> itemList = boardService.getList(searchCriteria).getContent();

        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }
}
