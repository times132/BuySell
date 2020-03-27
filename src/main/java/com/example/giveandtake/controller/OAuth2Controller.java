package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.*;
import com.example.giveandtake.common.AppException;
import com.example.giveandtake.domain.RoleName;
import com.example.giveandtake.mapper.UserMapper;
import com.example.giveandtake.model.entity.Role;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.repository.RoleRepository;
import com.example.giveandtake.repository.UserRepository;
import com.example.giveandtake.repository.UserRolesRepository;
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
import java.util.*;

@Controller
@RequestMapping("/oauth")
@AllArgsConstructor
public class OAuth2Controller{

    private final OAuth2AuthorizedClientService authorizedClientService;
    private UserService userService;
    private RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);
    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserRolesRepository userRolesRepository;

    @GetMapping(value = "/login")
    public String oauthlogin(@AuthenticationPrincipal Principal principal, HttpServletRequest request){
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


    private void kakaoOauth(KakaoDTO kakao){

        String username = "KA_" + kakao.getName();
        String kakaoemail = String.valueOf(kakao.getKakaoAccount().get("email"));

        if (!userService.usernameCheck(username)){ // 가입 안됬을 때
//            Set<Role> roles= kakao.getAuthorities()
//                    .stream()
//                    .map(role -> Role.builder().id((long)RoleName.valueOf(role.getAuthority()).ordinal()+1).name(RoleName.valueOf(role.getAuthority())).build())
//                    .collect(Collectors.toSet());
            UserRolesDTO userRole = new UserRolesDTO();
            Role role;
            if (kakaoemail.equals("null")){ //email이 없을 때
                System.out.println("롤게스트");
                role = roleRepository.findByName(RoleName.ROLE_GUEST)
                        .orElseThrow(() -> new AppException("User Role not set"));
            }
            else {
                System.out.println("롤유저");
                role = roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new AppException("User Role not set"));
            }

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setEmail(kakaoemail);
            userDTO.setName(String.valueOf(kakao.getAttributes().get("nickname")));
            userDTO.setNickname(String.valueOf(kakao.getAttributes().get("nickname")));
            userDTO.setProvider("kakao");
            User user = userRepository.save(userMapper.toEntity(userDTO));

            //ROLE 저장
            userRole.setRole(role);
            userRole.setUser(user);
            userRolesRepository.save(userMapper.userRolestoEntity(userRole));

        }
        UserDetails userDetails = userService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

    private void googleOauth(GoogleDTO google){
        logger.info("######GOOGLE : " + google);
        String username = "GO_" + google.getSub();
        UserDTO userDTO = new UserDTO();
        if (!userService.usernameCheck(username)){

            userDTO.setUsername(username);
            userDTO.setEmail(google.getEmail());
            userDTO.setNickname(String.valueOf(google.getAttributes().get("name")));
            userDTO.setProvider("google");
            User user = userRepository.save(userMapper.toEntity(userDTO));


            //ROLE 저장
            Role role = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set"));
            UserRolesDTO userRole = new UserRolesDTO();
            userRole.setUser(user);
            userRole.setRole(role);
            userRolesRepository.save(userMapper.userRolestoEntity(userRole));

        }

        UserDetails userDetails = userService.loadUserByUsername(username);
        System.out.println("AUTH ??"+userDetails.getAuthorities());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }
}
