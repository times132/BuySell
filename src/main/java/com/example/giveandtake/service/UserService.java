package com.example.giveandtake.service;


import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.common.AppException;
import com.example.giveandtake.common.CustomUserDetails;
import com.example.giveandtake.domain.RoleName;
import com.example.giveandtake.model.entity.Role;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.repository.RoleRepository;
import com.example.giveandtake.repository.UserRepository;
import com.sun.mail.imap.IMAPStore;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.security.Principal;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Transactional
    public Long joinUser(UserDTO userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        //회원가입을 처리하는 메서드이며, 비밀번호를 암호화하여 저장
//        logger.info("########rolefind : " + roleRepository.findByName(RoleName.ROLE_USER));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set"));
        userDto.setRoles(Collections.singleton(userRole));

        return userRepository.save(userDto.toEntity()).getId();
    }

    //로그인시 권한부여와 이메일과 패스워드를 User에 저장
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("email not found :" + email));

        return CustomUserDetails.create(user);
    }
    //유효성 검사
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        //유효성 검사에 실패한 필드에 정의된 메시지를 가져옵니다
        return validatorResult;
    }

    //회원정보 가져오기
    public UserDTO readUserByUsername(String username) {
        Optional<User> userWrapper;
        if(username.contains("@")){
            userWrapper = userRepository.findByEmail(username);
        }
        else {
            userWrapper = userRepository.findByUsername(username);
        }
        com.example.giveandtake.model.entity.User user = userWrapper.get();
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .name(user.getName())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();

    }
    //회원정보 삭제
    @Transactional
    public void delete(String username) {
        Optional<com.example.giveandtake.model.entity.User> userList = userRepository.findByUsername(username);
        com.example.giveandtake.model.entity.User user = userList.get();
        userRepository.deleteById(user.getId());
    }

    //회원정보 수정
    @Transactional
    public void modify(UserDTO userList){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        logger.info("###########AUTH principal: " + authentication.getPrincipal()); //커스텀 유저 반환
//        logger.info("###########AUTH name: " + authentication.getName()); // username
//        logger.info("###########AUTH authorities: " + authentication.getAuthorities()); // 권한
//        logger.info("###########AUTH credentital: " + authentication.getCredentials()); // 비밀번호
//        logger.info("###########AUTH detail: " + authentication.getDetails()); // 세션 같은거

        String password = ((CustomUserDetails) authentication.getPrincipal()).getPassword();
        if(!password.equals(userList.getPassword())) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userList.setPassword(passwordEncoder.encode(userList.getPassword()));
        }
        Set<Role> roles = new HashSet<>();

        for (GrantedAuthority a : authentication.getAuthorities()){
            roles.add(Role.builder()
                    .id(userList.getId())
                    .name(RoleName.valueOf(a.getAuthority()))
                    .build());
        }
        userList.setRoles(roles);

        userRepository.save(userList.toEntity()).getId();

        UserDetails userDetails = loadUserByUsername(userList.getEmail()); // 수정된 유저 정보 가져옴
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(userDetails, authentication, userDetails.getAuthorities());
        newAuth.setDetails(authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }


    //로그인 후 비밀번호 확인
    public boolean checkPassword(String pw , Principal principal) {
        Optional<com.example.giveandtake.model.entity.User> userList = userRepository.findByUsername(principal.getName());
        com.example.giveandtake.model.entity.User user = userList.get();
        String password = user.getPassword();
        System.out.println(password);
        System.out.println("입력한비번"+pw);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(pw,password)) {
            return true;
        }
        return false;
    }



    //아이디 중복확인
    public int usernameCheck(String username)  {
        Optional<com.example.giveandtake.model.entity.User> user = userRepository.findByUsername(username);
        System.out.println(username);
        System.out.println("값은 아이디 "+user.isPresent());
        if(user.isPresent()){
            return 1;
        }
        return 0;
    }

    //이메일 중복검사
    public int useridCheck(String email)
    {
        Optional<User> user = userRepository.findByEmail(email);
        System.out.println("값은 이메일 "+user.isPresent());
        if(user.isPresent()){
            return 1;
        }
        return 0;
    }
    //비밀번호 찾기
    @Transactional
    public void changePW(String email, String newPW){
        UserDTO userList = readUserByUsername(email);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userList.setPassword(passwordEncoder.encode(newPW));
        userRepository.save(userList.toEntity()).getId();
        System.out.println("비밀번호 찾기");

    }

}