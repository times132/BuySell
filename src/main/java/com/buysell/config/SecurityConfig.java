package com.buysell.config;

import com.buysell.domain.DTO.GoogleDTO;
import com.buysell.domain.DTO.KakaoDTO;
import com.buysell.security.CustomOAuth2UserService;
import com.buysell.service.UserService;
import com.buysell.security.CustomAuthenticationEntryPoint;
import com.buysell.security.CustomOAuth2Provider;
import com.buysell.security.MyOAuth2SuccessHandler;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity //Spring Security 설정할 클래스라고 정의
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private CustomAuthenticationEntryPoint authenticationEntryPoint;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private MyOAuth2SuccessHandler successhandler;

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/static/js/**", "/img/**", "/lib/**,");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.cors()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                // 페이지 권한 설정
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/chat/room").hasRole("USER")
                    .antMatchers("/user/info", "/board/write").authenticated()
                    .antMatchers("/**").permitAll()
                    .and()
                .formLogin()
                    .loginPage("/user/login")
                    .defaultSuccessUrl("/")
                    .permitAll()
                    .failureUrl("/user/login/error")
                    .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .customUserType(KakaoDTO.class, "kakao")
                        .customUserType(GoogleDTO.class, "google")
                        .and()
                    .successHandler(successhandler)
                    .and() // 로그아웃 설정
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/user/logout/result")
                    .invalidateHttpSession(true)
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/user/denied")// 403 예외처리 핸들링
                    .authenticationEntryPoint(authenticationEntryPoint); // Here;
    }

    @Bean  //BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체입니다.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false).userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
        super.setAuthenticationConfiguration(authenticationConfiguration);
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            OAuth2ClientProperties oAuth2ClientProperties,
            @Value("${spring.security.oauth2.client.registration.kakao.client-id}") String kakaoClientId,
            @Value("${spring.security.oauth2.client.registration.kakao.client-secret}") String kakaoClientSecret){

        List<ClientRegistration> registrations = oAuth2ClientProperties
                .getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret(kakaoClientSecret)
                .jwkSetUri("temp")
                .build());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client){
        if ("google".equals(client)){
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");

            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }

        return null;
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new CustomOAuth2UserService();
    }




}


