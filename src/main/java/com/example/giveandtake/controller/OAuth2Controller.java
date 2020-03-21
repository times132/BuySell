package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.GoogleDTO;
import com.example.giveandtake.DTO.KakaoDTO;
import com.example.giveandtake.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/oauth")
@AllArgsConstructor
public class OAuth2Controller{

    private final OAuth2AuthorizedClientService authorizedClientService;
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);

    @GetMapping(value = "/login")
    public String oauthlogin(@AuthenticationPrincipal Principal principal, HttpServletRequest request){
        HttpSession session = request.getSession();
        Map<String, Object> attributes = new HashMap<>();
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) principal;

        OAuth2AuthorizedClient oAuth2AuthorizedClient = authorizedClientService.loadAuthorizedClient(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(), oAuth2AuthenticationToken.getName());
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();
        OAuth2RefreshToken refreshToken = oAuth2AuthorizedClient.getRefreshToken();
//        KakaoDTO kakao = (KakaoDTO) oAuth2AuthenticationToken.getPrincipal();
//        logger.info("#######PRINCIPAL : " + principal);
//        logger.info("#######kakao : " + kakao);
//        logger.info("#######kakao account : " + kakao.getKakaoAccount());
//        logger.info("#######kakao proper : " + kakao.getProperties());
//        logger.info("#######kakao attr : " + kakao.getAttributes());
//        logger.info("#######PRINCIPAL : " + oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
//        logger.info("#######PRINCIPAL : " + oAuth2AuthenticationToken.getPrincipal().getAttributes());
//        logger.info("#######PRINCIPAL : " + accessToken);
//        logger.info("#######PRINCIPAL : " + refreshToken);
//        logger.info("#######PRINCIPAL : " + oAuth2AuthorizedClient.getAccessToken().getExpiresAt().atZone(ZoneId.of("Asia/Seoul")));
        String oauthclient = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        if (oauthclient.equals("google")){
            logger.info("구글");
            GoogleDTO google = (GoogleDTO) oAuth2AuthenticationToken.getPrincipal();
        }else if (oauthclient.equals("kakao")){
            KakaoDTO kakao = (KakaoDTO) oAuth2AuthenticationToken.getPrincipal();

            logger.info("#######kakao account : " + kakao.getKakaoAccount());
            logger.info("#######kakao proper : " + kakao.getProperties());
            logger.info("#######kakao attr : " + kakao.getAttributes());

            return "redirect:" + kakaoOauth(kakao, session);
        }



        return "/user/signup";
    }

    private String kakaoOauth(KakaoDTO kakao, HttpSession session){
        if ((boolean) kakao.getKakaoAccount().get("email_needs_agreement")){ // 이메일 동의 안했을때
            session.setAttribute("name", kakao.getProperties().get("nickname"));
            return "/user/signup";
        }else{ // 이메일 동의했을때
            Map<String, Object> kakaoaccount = kakao.getKakaoAccount();
            logger.info("이메일 동의 함"); // 가입했는지 확인
            logger.info("이메일: " + kakaoaccount.get("email"));
            if (userService.emailCheck(kakaoaccount.get("email").toString())){ // booleam으로 바꾸자
                logger.info("이미 가입된 이메일입니다.");
                UserDetails userDetails = userService.loadUserByUsername(kakaoaccount.get("email").toString());

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);

                return "/";
            }else { // 동의했는데 가입안됬을때
                logger.info("가입안됨");
                session.setAttribute("name", kakao.getProperties().get("nickname"));
                session.setAttribute("email", kakaoaccount.get("email"));
                return "/user/signup";
            }
        }
    }
}
