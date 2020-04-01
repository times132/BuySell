package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.GoogleDTO;
import com.example.giveandtake.DTO.KakaoDTO;
import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.DTO.UserRolesDTO;
import com.example.giveandtake.model.entity.Role;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.model.entity.UserRoles;
import com.example.giveandtake.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.*;

@Controller
@RequestMapping("/oauth")
@AllArgsConstructor
public class OAuth2Controller{

    private final OAuth2AuthorizedClientService authorizedClientService;
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);


    @GetMapping(value = "/login")
    public String oauthlogin(@AuthenticationPrincipal Principal principal, HttpServletRequest request) throws InterruptedException {
        HttpSession session = request.getSession();

        Map<String, Object> attributes = new HashMap<>();
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) principal;

        OAuth2AuthorizedClient oAuth2AuthorizedClient = authorizedClientService.loadAuthorizedClient(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(), oAuth2AuthenticationToken.getName());
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();
        OAuth2RefreshToken refreshToken = oAuth2AuthorizedClient.getRefreshToken();
//        KakaoDTO kakao = (KakaoDTO) oAuth2AuthenticationToken.getPrincipal();
        logger.info("#######PRINCIPAL : " + principal);
//        logger.info("#######kakao : " + kakao);
//        logger.info("#######kakao account : " + kakao.getKakaoAccount());
//        logger.info("#######kakao proper : " + kakao.getProperties());
//        logger.info("#######kakao attr : " + kakao.getAttributes());
//        logger.info("#######PRINCIPAL : " + oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
//        logger.info("#######PRINCIPAL : " + oAuth2AuthenticationToken.getPrincipal().getAttributes());
        logger.info("#######accesstoken : " + accessToken.getTokenValue());
//        logger.info("#######PRINCIPAL : " + refreshToken);
//        logger.info("#######PRINCIPAL : " + oAuth2AuthorizedClient.getAccessToken().getExpiresAt().atZone(ZoneId.of("Asia/Seoul")));
        String oauthclient = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        if (oauthclient.equals("google")){
            logger.info("구글");
            GoogleDTO google = (GoogleDTO) oAuth2AuthenticationToken.getPrincipal();
            googleOauth(google);

        }
        else if (oauthclient.equals("kakao")){
            KakaoDTO kakao = (KakaoDTO) oAuth2AuthenticationToken.getPrincipal();

//            logger.info("#######kakao account : " + kakao.getKakaoAccount());
//            logger.info("#######kakao proper : " + kakao.getProperties());
//            logger.info("#######kakao attr : " + kakao.getAttributes());
            logger.info("#######kakao attr : " + kakao.getAttributes());
            kakaoOauth(kakao);
        }
        return "redirect:/";
    }


    private void kakaoOauth(KakaoDTO kakao) {
        String username = "KA_" + kakao.getName();
        Authentication authentication;
        if (!userService.usernameCheck(username)) { // 가입 안됬을 때
            UserRolesDTO userRole = new UserRolesDTO();
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setEmail(String.valueOf(kakao.getKakaoAccount().get("email")));
            userDTO.setName(String.valueOf(kakao.getAttributes().get("nickname")));
            userDTO.setNickname(String.valueOf(kakao.getAttributes().get("nickname")));
            userDTO.setProvider("kakao");
            userService.joinUser(userDTO);
        }
            UserDetails userDetails = userService.loadUserByUsername(username);
            System.out.println("##AUTH ??" + userDetails.getAuthorities());
            authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            userService.updateSecurityContext(authentication, username);
    }

    private void googleOauth(GoogleDTO google) throws InterruptedException {
        logger.info("######GOOGLE : " + google);
        String username = "GO_" + google.getSub();
        UserDTO userDTO = new UserDTO();
        Authentication authentication;
        if (!userService.usernameCheck(username)) {
            userDTO.setUsername(username);
            userDTO.setEmail(google.getEmail());
            userDTO.setNickname(String.valueOf(google.getAttributes().get("name")));
            userDTO.setProvider("google");
            userService.joinUser(userDTO);
        }
            UserDetails userDetails = userService.loadUserByUsername(username);
            System.out.println("##AUTH ??" + userDetails.getAuthorities());
            authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            userService.updateSecurityContext(authentication, username);

    }
}
