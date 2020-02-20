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
    public String execSignup(@Valid UserDTO userDto, Errors errors, Model model, HttpServletRequest request) {

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
            
        }  //여기있는 코드를 보내고 싶어
        String code = mailService.sendMail(userDto.getEmail(), userDto.getUsername(), request);
        if(code  != "null")
        {

            return "redirect:/user/email";
        }
//        userService.joinUser(userDto);
        return "/user/signup";

    }
    //이메일페이지로 연결
    @GetMapping("user/email")
    public String email(){

        return "/user/email";
    }

    @RequestMapping(value = "/user/join_injeung{code}", method = RequestMethod.POST)
    public String  join_injeung(@PathVariable String code, String email_injeung, HttpServletResponse response_equals) throws IOException {
            //여기서 코드를 받아오고싶어어어어어ㅓ어엉

        System.out.println("이메일상의 코드 "+ code);

        System.out.println("입력한 코드 : : "+ email_injeung);


        //페이지이동과 자료를 동시에 하기위해 ModelAndView를 사용해서 이동할 페이지와 자료를 담음


        if (email_injeung.equals(code)) {

            //인증번호가 일치할 경우 인증번호가 맞다는 창을 출력하고 회원가입완료창으로 이동 후 데이터저장
            //한번더 입력할 필요가 없게 한다.

            response_equals.setContentType("text/html; charset=UTF-8");
            PrintWriter out_equals = response_equals.getWriter();
            out_equals.println("<script>alert('인증번호가 일치하였습니다. 회원가입이 완료되었습니다.');</script>");
            out_equals.flush();

            return "/user/login";


        }
        else if (email_injeung != code) {
            //이메일 인증번호와 일치하지 않을경우

            response_equals.setContentType("text/html; charset=UTF-8");
            PrintWriter out_equals = response_equals.getWriter();
            out_equals.println("<script>alert('인증번호가 일치하지않습니다. 인증번호를 다시 입력해주세요.'); history.go(-1);</script>");
            out_equals.flush();


            return "/user/email";

        }

        return "/user/login";

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
