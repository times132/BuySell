package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.common.Criteria;
import com.example.giveandtake.common.CustomUserDetails;
import com.example.giveandtake.common.Pagination;
import com.example.giveandtake.common.SearchCriteria;
import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.ChatRoom;
import com.example.giveandtake.model.entity.Like;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.service.AdminService;
import com.example.giveandtake.service.BoardService;
import com.example.giveandtake.service.MailService;
import com.example.giveandtake.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private MailService mailService;
    private BoardService boardService;
    private AdminService adminService;


    @GetMapping("/user/signup")
    public String signUpGET(HttpServletRequest request, Model model) {
        return "/user/signup";
    }
    //회원가입, 회원추가
    @PostMapping("/user/signup")
    public String signUpPOST(@Valid UserDTO userDto, Errors errors, Model model) {
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
    //readUserByUsername
    @RequestMapping(value = "/user/userInfo",method = RequestMethod.GET)
    public UserDTO userInfoGET(@RequestParam(value = "nickname") String nickname){
        return userService.readUserByUsername(nickname);
    }
    //<-------------------------------중복검사----------------------------------------------------------------------->
    //중복이메일 검사
    @RequestMapping(value = "/user/checkEmail", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkEmail(@RequestParam("email") String email) {
        return userService.checkEmail(email);
    }

    //중복 닉네임 검사
    @RequestMapping(value = "/user/checkNickname", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkNickname(@RequestParam("nickname") String nickname) {
        return userService.checkNickName(nickname);
    }

    //중복 아이디 검사
    @RequestMapping(value = "/user/checkUsername", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkUsername(@RequestParam("username") String username) {
        return userService.checkUserName(username);

    }

//<-------------------------------로그인----------------------------------------------------------------------->
    // 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "/user/login";
    }

    // 로그인 실패 페이지
    @GetMapping("/user/login/error")
    public void failureLogin(HttpServletResponse response)throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('로그인에 실패하였습니다.'); location.href='/user/login';</script>");
        out.flush();
    }

    // 로그아웃 후 홈으로 이동
    @GetMapping("/user/logout/result")
    public String logout() {
        return "redirect:/";
    }

    //아이디 찾기
    @GetMapping("/user/findID")
    public String findID(Model model, @RequestParam String email, @RequestParam String name)
    {
        List<User> userList = userService.findId(email, name);
        model.addAttribute("userList", userList);
        return "/user/findPW";
    }

    //비밀번호 찾기
    @GetMapping("/user/findPW")
    public String findPW()
    {
        return "/user/findPW";
    }


    //메일로 비밀번호 보내기
    @RequestMapping( value = "/user/findPW" , method=RequestMethod.POST)
    @ResponseBody
    public String passwordMailPOST(HttpServletRequest request, @RequestParam String username){
        String email = userService.getEmailByUsername(username);
        String mailType = "findpw";
        String alert = mailService.sendMail(email, request, mailType);
        HttpSession session = request.getSession();
        String key = (String) session.getAttribute("key");
        userService.changePW(username, key);
        session.removeAttribute("key");
        return alert;
    }


    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public void disAllowance(HttpServletResponse response)throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('접근 권한이 없습니다. 이메일 인증을 진행해 주세요'); location.href='/user';</script>");
        out.flush();

    }


//<----------------회원정보 --------------------------------------------------------------------------------------------------->
// 내 정보 페이지
    @GetMapping("/user")
    public String myInfoGET(Model model) {
        List<String> socialList = new ArrayList<String>(Arrays.asList("kakao", "google"));
        System.out.println(socialList);
        model.addAttribute("socialList", socialList);
        return "/user/myinfo";
    }

    // 회원 정보 수정

    @GetMapping ("/user/{userId}")
    public String userModifyGET() {
        return "/user/modifyuser";
    }

    //회원 정보 수정

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public void modifyPOST(UserDTO user, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if(!user.getProvider().equals("giveandtake")){
            userService.update(user);
            out.println("<script>alert('수정이 완료되었습니다.'); location.href='/user/info';</script>");
        }
        else{
            if (userService.checkPassword(user.getPassword())){
                userService.update(user);
                out.println("<script>alert('수정이 완료되었습니다.'); location.href='/user';</script>");
            }
            else
            {out.println("<script>alert('비밀번호가 틀립니다. 다시입력해주세요');history.go(-1);</script>");}
        }
        out.flush();
    }

    //비밀번호 변경
    @PostMapping("/user/changePW")
    @ResponseBody
    public ResponseEntity<String> passwordPOST(@RequestParam String newPW, @RequestParam String password,  @RequestParam String username){
        System.out.println("CHANGE PW");
        if(userService.checkPassword(password))
        {
            userService.changePW(username, newPW);
            return new ResponseEntity<>("비밀번호 변경이 완료되었습니다.", HttpStatus.OK);
        }
        else{

            return new ResponseEntity<>("기존의 비밀번호가 틀립니다.", HttpStatus.OK);
        }

    }
    //비밀번호 확인 후 탈퇴
    @RequestMapping(value = "/user/{userId}/delete",method = RequestMethod.GET)
    public void deleteUserGET(@RequestParam(value = "password") String password, @PathVariable ("userId") Long id, HttpSession httpSession, HttpServletResponse response) throws IOException{
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if(userService.checkPassword(password))
        {
            userService.delete(id);
            httpSession.invalidate();
            out.println("<script>alert('탈퇴가 완료되었습니다.'); location.href='/user/logout';</script>");
        }
        else{
            out.println("<script>alert('비밀번호가 틀립니다. 다시입력해주세요');history.go(-1);</script>");
        }
        out.flush();
    }

    @GetMapping(value = "/user/{userId}/boards")
    public String userBoardsGET(@PathVariable("userId") Long id, Model model, SearchCriteria searchCri){
        System.out.println(id);
        searchCri.setType("U");
        searchCri.setKeyword(String.valueOf(id));

        Page<Board> boardPage = boardService.getList(searchCri);

        model.addAttribute("user", userService.readUserById(id));
        model.addAttribute("boardList", boardPage.getContent());
        model.addAttribute("pageMaker", Pagination.builder()
                .cri(searchCri)
                .total(boardPage.getTotalElements())
                .realEndPage(boardPage.getTotalPages())
                .listSize(5) // 페이징 5로 설정
                .build());

        return "/user/userboard";
    }

    @PreAuthorize("principal.user.id == #id")
    @GetMapping(value = "/user/{userId}/likes")
    public String userLikesGET(@PathVariable("userId") Long id, Model model, SearchCriteria searchCri){
        searchCri.setType("L");
        searchCri.setKeyword(String.valueOf(id));
        Page<Like> likePage =  userService.getLikeList(id,searchCri);
        model.addAttribute("likeList", likePage.getContent());
        model.addAttribute("pageMaker", Pagination.builder()
                .cri(searchCri)
                .total(likePage.getTotalElements())
                .realEndPage(likePage.getTotalPages())
                .listSize(5) // 페이징 5로 설정
                .build());

        return "/user/likeList";
    }
}