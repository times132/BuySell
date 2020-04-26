package com.buysell.security;

import com.buysell.domain.DTO.GoogleDTO;
import com.buysell.domain.DTO.KakaoDTO;
import com.buysell.domain.DTO.UserDTO;
import com.buysell.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
        throw new NotImplementedException();
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication authentication) {
        String oauthClient = authorizedClient.getClientRegistration().getRegistrationId();
//        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        if (oauthClient.equals("google")){
            GoogleDTO google = (GoogleDTO) oAuth2AuthenticationToken.getPrincipal();
            googleOauth(google);

        }
        else if (oauthClient.equals("kakao")){
            KakaoDTO kakao = (KakaoDTO) oAuth2AuthenticationToken.getPrincipal();

            if (kakaoOauth(kakao)){
                authentication.setAuthenticated(false);
            }
        }

    }

    private Boolean kakaoOauth(KakaoDTO kakao) {
        String username = kakao.getName();
        System.out.println(username);
        Boolean isExist = false; // 이메일 동의 여부

        if (!userService.checkUserName(username)) { // 가입 안됬을 때
            UserDTO userDTO = new UserDTO();
            String email = String.valueOf(kakao.getKakaoAccount().get("email"));
            if ("null".equals(email)){
                userDTO.setEmail(null);
                userDTO.setActivation(false);
            }else if (userService.checkEmail(email)){
                return true;
            }else {
                userDTO.setEmail(email);
            }
            userDTO.setUsername(username);
            userDTO.setName(String.valueOf(kakao.getAttributes().get("name")));
            userDTO.setNickname(String.valueOf(kakao.getAttributes().get("name")));
            userDTO.setProvider("kakao");
            userService.joinUser(userDTO);
        }
        return isExist;
    }

    private void googleOauth(GoogleDTO google){
        String username = google.getName();
        UserDTO userDTO = new UserDTO();

        if (!userService.checkUserName(username)) {
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
