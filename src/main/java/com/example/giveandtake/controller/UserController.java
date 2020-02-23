package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.repository.UserRepository;
import com.example.giveandtake.service.MailService;
import com.example.giveandtake.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;
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


//<-------------------------------로그인----------------------------------------------------------------------->
    // 로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "/user/login";
    }

    // 로그인 성공 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "/user/successlogin";
    }
    // 로그인 실패 페이지
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


//<----------------회원정보 --------------------------------------------------------------------------------------------------->
    // 내 정보 페이지
    @GetMapping("/user/info")
    public String dispMyInfo(Principal principal, Model model) {
        UserDTO userList = userService.readUserByEmail(principal.getName());
        model.addAttribute("userList",userList);
        return "/user/myinfo";
    }

    // 회원 정보 수정
    @GetMapping ("/user/modifyuser")
    public String dismodifyuser() {
        return "/user/modifyuser";
    }

    @PostMapping ("/user/modifyuser")
    public String modifyuser(UserDTO userList ,HttpServletResponse response) throws IOException {
        userService.modify(userList);
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out_equals = response.getWriter();
        out_equals.println("<script>alert('수정이 완료되었습니다.');location.replace('/user/info');</script>");
        out_equals.flush();
        return "redirect:/user/info";
    }

    // 회원 탈퇴
    @GetMapping ("/user/password")
    public String disdeleteuser() {
        return "/user/password";
    }

    @PostMapping ("/user/password")
    public String disdeleteuser(String password,Principal principal){
        String email = principal.getName();
        if(userService.checkPassword(password))
        {
            userService.delete(email);
            return "user/deleteuser";
        }

        return "/user/password";
    }

    @GetMapping("/user/deleteuser")
    public String disdeleteuser( Principal principal) {
        return "/user/deleteuser";
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/admin";
    }



}