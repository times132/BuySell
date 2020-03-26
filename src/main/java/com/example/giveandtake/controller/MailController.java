package com.example.giveandtake.controller;

import com.example.giveandtake.service.MailService;
import com.example.giveandtake.service.UserService;
import lombok.AllArgsConstructor;
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

    //이메일 인증 페이지 맵핑 메소드
    @RequestMapping("/user/email")
    public String email() {
        return "/user/email";
    }



    @PostMapping("/user/auth")
    @ResponseBody
    public String sendmail(@RequestParam String email, HttpServletRequest request) {
        System.out.println("이메일은" + email);
        String mailType =  "join";
        String alert = mailService.sendMail(email, request, mailType);

        return alert;
    }


    @RequestMapping(value = "/mail/checkCode", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkCode(@RequestParam String codekey, @RequestParam String email, HttpServletRequest request) {

        boolean note= mailService.checkCode(request, codekey, email);
        System.out.println(note);
        return note;
    }

}
