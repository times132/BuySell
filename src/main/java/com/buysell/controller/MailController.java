package com.buysell.controller;

import com.buysell.service.MailService;
import com.buysell.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    private final UserService userService;

    @GetMapping("/user/auth")
    @ResponseBody
    public String sendMailGET(@RequestParam String email, HttpServletRequest request) {
        System.out.println("이메일 전송 " + email);
        String mailType =  "join";
        String alert = mailService.sendMail(email, request, mailType);

        return alert;
    }

    //이메일로 전송된 코드확인
    @RequestMapping(value = "/mail/checkCode", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkCode(@RequestParam String codeKey, @RequestParam String email, @RequestParam String username, HttpServletRequest request) {

        boolean note= mailService.checkCode(request, codeKey); //note : 이메일 인증코드 일치 여부
        if(username.equals(null) && note){ //회원 아이디가 없는 경우
            return note;
        }
        else if(userService.checkUserName(username) && note) { //회원이 있는 경우 + 6자리의 코드가 일치하는 경우
            userService.changeROLE(email);
        }
        return note;
    }

}
