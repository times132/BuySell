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
    private MailService mailService;


    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String dispSignup() {

        return "/user/signup";
    }

    //중복이메일 검사
    @RequestMapping(value = "/user/idCheck", method = RequestMethod.GET)
    @ResponseBody
    public int idCheck(@RequestParam("email") String email) {
        System.out.println("##############################이메일은"+email);
        return userService.useridCheck(email);
    }

    //중복아이디 검사
    @RequestMapping(value = "/user/usernameCheck", method = RequestMethod.GET)
    @ResponseBody
    public int usernameCheck(@RequestParam("username") String username) {
        System.out.println(username);
        return userService.usernameCheck(username);
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

    @GetMapping("/user/findpw")
    public String findpw()
    {
        return "/user/findPW";
    }
    //비밀번호 찾기
    @RequestMapping( value = "/user/findpw.do{email}" , method=RequestMethod.GET)
    public String findPW(HttpServletRequest request, @PathVariable String email) throws IOException {
        System.out.println("##########################################이메일"+ email);
        String mailType = "findpw";
        String code = mailService.sendMail(email, request, mailType);

        userService.changePW(email, code);
        System.out.println("비밀번호변경완료");


        return "/user/login";
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
        UserDTO userList = userService.readUserByUsername(principal.getName());
        model.addAttribute("userList",userList);
    return "/user/myinfo";
    }

    // 회원 정보 수정
    @GetMapping ("/user/modifyuser")
    public String dismodifyuser() {
        return "/user/modifyuser";
    }

    @PostMapping ("/user/modifyuser")
    public String modifyuser(UserDTO userList) throws IOException {
        userService.modify(userList);
        return "redirect:/user/info";
    }
    // 회원 탈퇴
    @GetMapping ("/user/password")
    public String disdeleteuser() {
        return "/user/password";
    }
    //비밀번호 확인 후 탈퇴
    @PostMapping ("/user/password")
    public int disdeleteuser(String password,Principal principal, HttpSession httpSession){

        if(userService.checkPassword(password,principal))
        {
            userService.delete(principal.getName());
            httpSession.invalidate();
            return  1;
        }

        return 0;
    }


    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/admin";
    }



}