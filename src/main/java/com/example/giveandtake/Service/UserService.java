package com.example.giveandtake.Service;


import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.domain.Role;
import com.example.giveandtake.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public UserDetails  loadUserByUsername(String email) throws UsernameNotFoundException {
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
}



