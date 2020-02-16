package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@AllArgsConstructor
public class UserController {
    private UserService userService;

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
}
