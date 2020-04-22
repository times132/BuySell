package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.ReplyDTO;
import com.example.giveandtake.common.Criteria;
import com.example.giveandtake.common.CustomUserDetails;
import com.example.giveandtake.model.entity.Reply;
import com.example.giveandtake.service.ReplyService;
import com.example.giveandtake.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("replies")
@AllArgsConstructor
public class ReplyController {

    private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

    private ReplyService replyService;
    private UserService userService;

    // 댓글 등록
    @PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE}) //json 방식으로 데이터를 받음
    public ResponseEntity<String> writePOST(@RequestBody ReplyDTO replyDTO, @AuthenticationPrincipal CustomUserDetails userDetails){
        // POST 방식으로 json 데이터를 받아 @RequestBody를 이용하여 Reply 타입으로 변환
        logger.info("-----reply writePOST-----");
        replyService.writeReply(replyDTO, userDetails);
        return new ResponseEntity<>("댓글 등록이 완료되었습니다.", HttpStatus.OK);
    }

    // 댓글 페이지 가져오기
    @GetMapping(value = "/pages/{bid}/{page}", produces = "application/json") // json 방식으로 리턴해줌
    public ResponseEntity<Page<Reply>> listGET(@PathVariable("bid") Long bid, @PathVariable("page") Integer page){
        logger.info("-----Reply readListGET-----");
        if (page == -1) page = 1;
        Criteria cri = new Criteria(page, 5);

        return new ResponseEntity<>(replyService.readReplyList(bid, cri), HttpStatus.OK);
    }

    // 댓글 하나 읽기
    @GetMapping(value = "{rid}", produces = "application/json")
    public ResponseEntity<ReplyDTO> readGET(@PathVariable("rid") Long rid){
        logger.info("-----reply readGET-----");

        return new ResponseEntity<>(replyService.readReply(rid), HttpStatus.OK);
    }

    // 댓글 수정
    @PutMapping(value = "{rid}", consumes = "application/json")
    public ResponseEntity<String> modifyPOST(@RequestBody ReplyDTO replyDTO, @PathVariable("rid") Long rid, @AuthenticationPrincipal CustomUserDetails userDetails){
        logger.info("-----reply modifyPUT-----");

        return replyService.updateReply(replyDTO, userDetails).equals(rid) ? new ResponseEntity<>("댓글 수정이 완료되었습니다.", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 댓글 삭제
    @PreAuthorize("principal.user.nickname == #dto.replyer")
    @DeleteMapping(value = "{rid}")
    public ResponseEntity<String> replyDELETE(@RequestBody ReplyDTO dto, @PathVariable("rid") Long rid){
        logger.info("-----reply removeDELETE-----");
        logger.info("dto : " + dto);
        replyService.deleteReply(rid);

        return new ResponseEntity<>("댓글 삭제가 완료되었습니다.", HttpStatus.OK);
    }

}