package com.example.giveandtake.service;


import com.example.giveandtake.DTO.BoardDTO;
import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.domain.Role;
import com.example.giveandtake.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Transactional
    public Long joinUser(UserDTO userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        //회원가입을 처리하는 메서드이며, 비밀번호를 암호화하여 저장

        return userRepository.save(userDto.toEntity()).getId();
    }

    //로그인시 권한부여와 이메일과 패스워드를 User에 저장
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<com.example.giveandtake.model.entity.User> userWrapper = userRepository.findByEmail(email);
        com.example.giveandtake.model.entity.User user = userWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin@example.com").equals(email)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(user.getEmail(), user.getPassword(), authorities);//SpringSecurity에서 제공하는 UserDetails를 구현한 User를 반환
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
    public UserDTO readUserByEmail(String email) {
        Optional<com.example.giveandtake.model.entity.User> userWrapper = userRepository.findByEmail(email);
        com.example.giveandtake.model.entity.User user = userWrapper.get();
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .build();

    }
    //회원정보 삭제
    public void delete(String email) {
        Optional<com.example.giveandtake.model.entity.User> userList = userRepository.findByEmail(email);
        com.example.giveandtake.model.entity.User user = userList.get();
        userRepository.deleteById(user.getId());
    }

     //회원정보 수정
    public void modify(UserDTO userList){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userList.setPassword(passwordEncoder.encode(userList.getPassword()));
        userRepository.save(userList.toEntity()).getId();
    }


    //로그인 후 비밀번호 확인
    public boolean checkPassword(String pw) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;
        String password = ((UserDetails) principal).getPassword();
        System.out.println(password);
        System.out.println("입력한비번"+pw);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(pw,password)) {
            return true;
        }
        return false;
    }


}