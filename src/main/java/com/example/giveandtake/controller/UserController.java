package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.Service.MailService;
import com.example.giveandtake.Service.UserService;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Callable;

@Controller
@AllArgsConstructor
public class UserController {


    private MailService mailService;

    private UserService userService;

    private UserRepository memberDao;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String dispSignup() {

        return "/user/signup";
    }
    @PostMapping("/user/signup")
    public String execSignup(@Valid UserDTO userDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            // 회원가입 실패시, 입력 데이터를 유지
            model.addAttribute("userDto", userDto);
            //회원가입 실패 시, 회원가입 페이지에서 입력했던 정보들을 그대로 유지하기 위해 입력받았던 데이터를 그대로 할당


            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "/user/signup";
        }

        userService.joinUser(userDto);
        return "redirect:/user/login";
    }


    // mailSending 코드
    @RequestMapping( value = "/user/auth.do" , method=RequestMethod.POST )
    public ModelAndView mailSending(HttpServletRequest request, String email, HttpServletResponse response_email) throws IOException {
        String code = mailService.sendMail(email, request);
        ModelAndView mv = new ModelAndView();    //ModelAndView로 보낼 페이지를 지정하고, 보낼 값을 지정한다.
        mv.setViewName("/user/email_injeung");     //뷰의이름

        if(code  != "null")
        {
            logger.info("이메일 코드 : " + code);
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


    //이메일 인증 페이지 맵핑 메소드
    @RequestMapping("/user/email")
    public String email() {
        return "/user/email";
    }

    @RequestMapping(value = "/user/join_injeung.do{code},{email}", method = RequestMethod.POST)
    public ModelAndView join_injeung(@PathVariable String code,  @PathVariable String email, String email_injeung, HttpServletResponse response_equals) throws IOException {

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



    // 로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "/user/login";
    }

    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "/user/successlogin";
    }

    @GetMapping("/user/login/error")
    public String dispFailurLogin(){
        return "/user/failurelogin";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "/user/logout";
    }


    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "/user/denied";
    }

    // 내 정보 페이지
    @GetMapping("/user/info")
    public String dispMyInfo(Principal principal, Model model) {
        String email = principal.getName();
        logger.info("user email : " + email);
        Optional<com.example.giveandtake.model.entity.User> userWrapper = memberDao.findByEmail(email);
        com.example.giveandtake.model.entity.User user = userWrapper.get();

        model.addAttribute("userList",user);

        return "/user/myinfo";
    }

    // 내정보 상세정보
    @GetMapping("/user/detail")
    public String dispuserdetail() {
        return "/user/detail";
    }
    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/admin";
    }

    @GetMapping("/login/email")
    public String emailcode() {
        return "/user/email";
    }




}
