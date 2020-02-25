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


@Controller
@AllArgsConstructor
public class MailController {
    private MailService mailService;

    //이메일 인증 페이지 맵핑 메소드
    @RequestMapping("/user/email")
    public String email() {
        return "/user/email";
    }




// mailSending 코드
    @RequestMapping( value = "/user/auth.do{email}" , method=RequestMethod.GET )
    public ModelAndView mailSending(HttpServletRequest request, @PathVariable String email, HttpServletResponse response_email) throws IOException {
        String mailType =  "join";
        String code = mailService.sendMail(email, request, mailType);
        ModelAndView mv = new ModelAndView();    //ModelAndView로 보낼 페이지를 지정하고, 보낼 값을 지정한다.
        mv.setViewName("/user/email_injeung");     //뷰의이름

        if(code  != "null")
        {
            mv.addObject("code", code);
            mv.addObject("email", email);
            mv.setViewName("/user/email_injeung");
        }

        System.out.println("mv : "+mv);

        response_email.setContentType("text/html; charset=UTF-8");
        PrintWriter out_email = response_email.getWriter();
        out_email.println("<script>alert('이메일이 발송되었습니다. 인증번호를 입력해주세요.');</script>");
        out_email.flush();

        return mv;

    }



    @RequestMapping(value = "/user/join_injeung.do{code},{email}", method = RequestMethod.POST)
    public ModelAndView join_injeung(@PathVariable String code, @PathVariable String email, String email_injeung, HttpServletResponse response_equals) throws IOException {

        System.out.println("이메일상의 코드 "+ code);

        System.out.println("입력한 코드 : : "+ email_injeung);

        //페이지이동과 자료를 동시에 하기위해 ModelAndView를 사용해서 이동할 페이지와 자료를 담음

        ModelAndView mv = new ModelAndView();

        mv.setViewName("/user/signup");

        mv.addObject("email",email);

        if (email_injeung.equals(code)) {

            //인증번호가 일치할 경우 인증번호가 맞다는 창을 출력하고 회원가입창으로 이동함
            mv.setViewName("/user/signup");
            mv.addObject("email",email);

            //만약 인증번호가 같다면 이메일을 회원가입 페이지로 같이 넘겨서 이메일을
            //한번더 입력할 필요가 없게 한다.

            response_equals.setContentType("text/html; charset=UTF-8");
            PrintWriter out_equals = response_equals.getWriter();
            out_equals.println("<script>alert('인증번호가 일치하였습니다. 회원가입창으로 이동합니다.');</script>");
            out_equals.flush();
            return mv;


        }
        else if (email_injeung != code) {


            ModelAndView mv2 = new ModelAndView();

            mv2.setViewName("/user/email");

            response_equals.setContentType("text/html; charset=UTF-8");
            PrintWriter out_equals = response_equals.getWriter();
            out_equals.println("<script>alert('인증번호가 일치하지않습니다. 이메일을 다시 입력해주세요'); history.go(-2);</script>");
            out_equals.flush();
            return mv2;

        }
        return mv;
    }

}
