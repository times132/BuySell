package com.buysell.controller;

import com.buysell.service.MailService;
import com.buysell.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class MailController {
    private MailService mailService;
    @Autowired
    private UserService userService;

    @GetMapping("/user/auth")
    @ResponseBody
    public String sendMailGET(@RequestParam String email, HttpServletRequest request) {
        System.out.println("이메일 전송" + email);
        String mailType =  "join";
        String alert = mailService.sendMail(email, request, mailType);

        return alert;
    }

    //이메일로 전송된 코드확인
    @RequestMapping(value = "/mail/checkCode", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkCode(@RequestParam String codeKey, @RequestParam String email, HttpServletRequest request) {

        boolean note= mailService.checkCode(request, codeKey, email);
        if(userService.checkEmail(email) && note) { //회원이 있는 경우 + 6자리의 코드가 일치하는 경우
            userService.changeROLE(email);
        }
        return note;
    }

}
