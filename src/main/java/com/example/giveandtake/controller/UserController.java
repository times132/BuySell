package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.BoardFileDTO;
import com.example.giveandtake.DTO.ChatRoomDTO;
import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.common.CustomUserDetails;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.repository.UserRepository;
import com.example.giveandtake.service.MailService;
import com.example.giveandtake.service.UserService;
import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.*;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private MailService mailService;


    @GetMapping("/user/signup")
    public String Signup() {
        return "/user/signup";
    }


    @RequestMapping(value = "/user/activate")
    @ResponseBody
    public ResponseEntity<String> activateUser(@RequestParam(value = "email") String email, Principal principal){

        userService.changeAct(email, principal);
        return new ResponseEntity<>("계정이 활성화 되었습니다.", HttpStatus.OK);
    }


    //중복아이디 검사
    @RequestMapping(value = "/user/usernameCheck", method = RequestMethod.GET)
    @ResponseBody
    public int usernameCheck(@RequestParam("username") String username) {
        System.out.println(username);
        return userService.usernameCheck(username);
    }

    //회원가입
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
        return "redirect:/user/login";
    }

    // 로그아웃 후 홈으로 이동
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "redirect:/";
    }

    @GetMapping("/user/findpw")
    public String findpw()
    {
        return "/user/findPW";
    }

    //비밀번호 찾기
    @RequestMapping( value = "/user/findpw/{email}" , method=RequestMethod.GET)
    public String findPW(HttpServletRequest request, @PathVariable String email) throws IOException {
        System.out.println("##########################################이메일"+ email);
        String mailType = "findpw";
        String code = mailService.sendMail(email, request, mailType);
        userService.changePW(email, code);

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
    public String dispMyInfo() {
    return "/user/myinfo";
    }

    // 회원 정보 수정
    @GetMapping ("/user/modifyuser")
    public String dismodifyuser() {
        return "/user/modifyuser";
    }

    //회원정보수정
    @RequestMapping(value = "/user/modifyuser", method = RequestMethod.POST)
    @ResponseBody
    public void modifyuser(UserDTO userList, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if(userService.checkPassword(userList.getPassword())){
            userService.modify(userList);
            out.println("<script>alert('수정이 완료되었습니다.'); location.href='/user/info';</script>");
        }
        else{
            out.println("<script>alert('비밀번호가 틀립니다. 다시입력해주세요');history.go(-1);</script>");
        }
        out.flush();
    }
    //비밀번호변경
    @PutMapping("/user/changePW")
    @ResponseBody
    public ResponseEntity<String> changePW(@RequestParam String newPW, @RequestParam String password , Principal principal){
        System.out.println("CHANGE PW");
        if(userService.checkPassword(password))
        {
            userService.changePW(principal.getName(), newPW);
            return new ResponseEntity<>("비밀번호 변경이 완료되었습니다.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("기존의 비밀번호가 틀립니다.", HttpStatus.OK);
        }

    }

    //비밀번호 확인 후 탈퇴
    @PostMapping ("/user/delete")
    public void disdeleteuser(String password,Principal principal, HttpSession httpSession, HttpServletResponse response) throws IOException{
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if(userService.checkPassword(password))
        {
            userService.delete(principal.getName());
            httpSession.invalidate();
            out.println("<script>alert('탈퇴가 완료되었습니다.'); location.href='/user/logout';</script>");
        }
        else{
            out.println("<script>alert('비밀번호가 틀립니다. 다시입력해주세요');history.go(-1);</script>");
        }
        out.flush();
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/admin";
    }


}