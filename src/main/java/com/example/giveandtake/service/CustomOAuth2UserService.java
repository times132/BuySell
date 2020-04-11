package com.example.giveandtake.service;

import com.example.giveandtake.DTO.GoogleDTO;
import com.example.giveandtake.DTO.KakaoDTO;
import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.DTO.UserRolesDTO;
import com.example.giveandtake.controller.OAuth2Controller;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


@RequiredArgsConstructor
@AllArgsConstructor
@Service
public class CustomOAuth2UserService  implements OAuth2AuthorizedClientService {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Autowired
    private UserService userService;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        logger.info("#######principalName : " + principalName);
        throw new NotImplementedException();
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication authentication) {
        String oauthclient = authorizedClient.getClientRegistration().getRegistrationId();
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        logger.info("#######PRINCIPAL : " + oAuth2AuthenticationToken.getPrincipal().getAttributes());
        logger.info("#######oauthclient : " + oauthclient);
        String username = "";
        if (oauthclient.equals("google")){
            logger.info("구글");
            GoogleDTO google = (GoogleDTO) oAuth2AuthenticationToken.getPrincipal();
            googleOauth(google);

        }
        else if (oauthclient.equals("kakao")){
            KakaoDTO kakao = (KakaoDTO) oAuth2AuthenticationToken.getPrincipal();
            logger.info("#######kakao account : " + kakao.getKakaoAccount());
            logger.info("#######accesstoken : " + accessToken.getTokenValue());
            logger.info("#######kakao attr : " + kakao.getAttributes());
            logger.info("#######kakao properties : " + kakao.getProperties());
            logger.info("#######kakao properties : " + kakao.getProperties());
            logger.info("#######kakao : " + kakao);
            kakaoOauth(kakao);
        }

    }

    private void kakaoOauth(KakaoDTO kakao) {
        String username = kakao.getId();
        System.out.println(username);
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
    }

    private void googleOauth(GoogleDTO google){
        logger.info("######GOOGLE : " + google);
        String username = google.getSub();
        UserDTO userDTO = new UserDTO();
        Authentication authentication;
        if (!userService.usernameCheck(username)) {
            userDTO.setUsername(username);
            userDTO.setEmail(google.getEmail());
            userDTO.setNickname(String.valueOf(google.getAttributes().get("name")));
            userDTO.setProvider("google");
            userService.joinUser(userDTO);
        }
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        throw new NotImplementedException();
    }
}
