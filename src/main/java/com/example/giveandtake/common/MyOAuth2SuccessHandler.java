package com.example.giveandtake.common;

import com.example.giveandtake.service.CustomOAuth2UserService;
import com.example.giveandtake.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(MyOAuth2SuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.isAuthenticated()){
            String id = authentication.getName();
            CustomUserDetails userDetails = (CustomUserDetails)userService.loadUserByUsername(id);
            authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();

            if (userDetails.getUser().getEmail() == null){
                out.println("<script>alert('이메일 인증 후 추가 서비스 이용이 가능합니다.'); location.href='/user/info';</script>");
            }else {
                out.println("<script>alert('로그인이 완료되었습니다.'); location.href='/';</script>");
            }



            out.flush();
        } else{
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('이미 가입된 이메일입니다.'); location.href='/';</script>");
            out.flush();
        }
    }
}