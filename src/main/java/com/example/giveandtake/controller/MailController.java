package com.example.giveandtake.controller;

import com.example.giveandtake.service.MailService;
import com.example.giveandtake.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;


@Controller
@AllArgsConstructor
public class MailController {
    private MailService mailService;
    @Autowired
    private UserService userService;


    @GetMapping("/user/auth")
    @ResponseBody
    public String sendMailGET(@RequestParam String email, HttpServletRequest request) {
        System.out.println("이메일" + email);
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
