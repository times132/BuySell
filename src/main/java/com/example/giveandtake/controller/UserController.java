package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class UserController {
    private UserService userService;

    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String dispSignup() {
        return "/user/signup";
    }

    // 회원가입 처리
    @PostMapping("/user/signup")
    public String execSignup(UserDTO userDto) {
        userService.joinUser(userDto);

        return "redirect:/user/login";
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
    public String dispMyInfo() {
        return "myinfo";
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
}
