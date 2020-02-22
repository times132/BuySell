package com.example.giveandtake.service;


import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.domain.Role;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

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
        userDto.setNickname(userDto.getNickname());
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

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);//SpringSecurity에서 제공하는 UserDetails를 구현한 User를 반환
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        //유효성 검사에 실패한 필드에 정의된 메시지를 가져옵니다
        return validatorResult;
    }

    //상세정보 가져오기
    public Optional<com.example.giveandtake.model.entity.User> readUserByEmail(String email) {
        Optional<com.example.giveandtake.model.entity.User> userList = userRepository.findByEmail(email);

        return userList;
    }

    public void delete(String email) {
        Optional<com.example.giveandtake.model.entity.User> userList = userRepository.findByEmail(email);
        Long id = userList.get().getId();
        userRepository.deleteById(id);
    }


    public boolean checkPassword(String password, String pw) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(password, pw)) {
            return true;
        }
        return false;
    }


}