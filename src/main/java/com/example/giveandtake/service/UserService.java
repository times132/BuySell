package com.example.giveandtake.service;


import com.example.giveandtake.DTO.UserDTO;
import com.example.giveandtake.DTO.UserRolesDTO;
import com.example.giveandtake.common.AppException;
import com.example.giveandtake.common.CustomUserDetails;
import com.example.giveandtake.domain.RoleName;
import com.example.giveandtake.mapper.UserMapper;
import com.example.giveandtake.model.entity.ChatUsers;
import com.example.giveandtake.model.entity.Role;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.model.entity.UserRoles;
import com.example.giveandtake.repository.RoleRepository;
import com.example.giveandtake.repository.UserRepository;
import com.example.giveandtake.repository.UserRolesRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRolesRepository userRolesRepository;
    private ChatService chatService;

    //회원가입을 처리하는 메서드이며, 비밀번호를 암호화하여 저장
    @Transactional
    public void joinUser(UserDTO userDto) {
        User user = new User();
        Role role = new Role();
        if (userDto.getProvider() == null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userDto.setActivation(true);
            userDto.setProvider("giveandtake");
            user = userRepository.save(userMapper.toEntity(userDto));

            //Role 저장
            role = roleRepository.findByName(RoleName.ROLE_GUEST)
                    .orElseThrow(() -> new AppException("User Role not set"));
        }
        else {
            user = userRepository.save(userMapper.toEntity(userDto));
            role = roleRepository.findByName(RoleName.ROLE_GUEST)
                    .orElseThrow(() -> new AppException("User Role not set"));
            if (user.getEmail() != null){
                role = roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new AppException("User Role not set"));
            }
//            if(!user.getEmail().equals("null")){
//
//            }
        }
        UserRolesDTO userRole = new UserRolesDTO();
        userRole.setUser(user);
        userRole.setRole(role);
        UserRoles userRoles = userRolesRepository.save(userMapper.userRolestoEntity(userRole));
        user.getRoles().add(userRoles);
    }

    //로그인시 권한부여와 이메일과 패스워드를 User에 저장
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(name);
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
        User user;
        if(username.contains("@")){
            user = userRepository.findByEmail(username);
        }
        else {
            user = userRepository.findByUsername(username);
        }

        return userMapper.convertEntityToDto(user);
    }

    public UserDTO readUserById(Long id){
        User user = userRepository.findById(id).get();

        return UserDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .build();
    }


    //회원정보 삭제
    @Transactional
    public void delete(Long userId) {
        User user = userRepository.findById(userId).get();
        List<ChatUsers> chatUsers = user.getChats();
        userRepository.deleteById(userId);
        for (ChatUsers chatUser : chatUsers){
            chatService.deleteChatRoom(chatUser.getChatRoom().getRoomId(), user.getNickname());
        }

        return;
    }

    //회원정보 수정
    @Transactional
    public void modify(UserDTO userList){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userList.setPassword(((CustomUserDetails) authentication.getPrincipal()).getPassword());
        userList.setRoles(((CustomUserDetails) authentication.getPrincipal()).getUser().getRoles());
        userRepository.save(userMapper.toEntity(userList)).getId();
        updateSecurityContext(authentication , userList.getUsername());
    }

    //시큐리티컨텍스트 업데이트
    public void updateSecurityContext(Authentication authentication, String username) {

        UserDetails userDetails = loadUserByUsername(username); // 수정된 유저 정보 가져옴
        System.out.println("걸림?? ##########AUTH#############"+userDetails.getAuthorities());
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(userDetails, authentication, userDetails.getAuthorities());
        newAuth.setDetails(authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    //사진변경
    public void uploadProfile(String fileName, Long uid){
        Optional<User> userWapper = userRepository.findById(uid);
        User user = userWapper.get();
        UserDTO userDTO = userMapper.convertEntityToDto(user);
        userDTO.setProfileImage(fileName);
        userRepository.save(userMapper.toEntity(userDTO));
    }


    //로그인 후 비밀번호 확인
    public boolean checkPassword(String pw) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String password = ((CustomUserDetails) authentication.getPrincipal()).getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(pw,password)) {
            return true;
        }
        return false;
    }



    //아이디 중복확인
    @Transactional
    public boolean usernameCheck(String username)  {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        System.out.println(username);
        System.out.println("아이디 중복확인" + user.isPresent());
        if(user.isPresent()){
            return true;
        }
        return false;
    }

    //이메일검사
    @Transactional
    public boolean emailCheck(String email)
    {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        System.out.println("값");
        if(user.isPresent()){
            return true;
        }
        return false;
    }
    //닉네임 중복검사
    @Transactional
    public boolean nicknameCheck(String nickname)
    {
        Optional<User> user = Optional.ofNullable(userRepository.findByNickname(nickname));
        System.out.println("값 "+user);
        if(user.isPresent()){
            return true;
        }
        return false;
    }
    //비밀번호 변경
    @Transactional
    public void changePW(String username, String newPW){
        User user = userRepository.findByUsername(username);

        UserDTO userDTO = userMapper.convertEntityToDto(user);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDTO.setPassword(passwordEncoder.encode(newPW));
        userRepository.save(userMapper.toEntity(userDTO)).getId();

    }
    //계정 USER 로 변환
    public void changeAct(String email) {
        System.out.println("EMAIL"+email);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByNickname(authentication.getName());
        Set<UserRoles> userRoles = user.getRoles();

        userRoles.clear(); //Role 모두 지우기
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set"));
        UserRolesDTO userRole = new UserRolesDTO();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoles.add(userMapper.userRolestoEntity(userRole));
        UserDTO userDTO = userMapper.convertEntityToDto(user);
        userDTO.setEmail(email);
        userDTO.setRoles(userRoles);

        userRepository.save(userMapper.toEntity(userDTO));
        updateSecurityContext(authentication , userDTO.getUsername());
    }

    //이메일 반환
    public String  getEmailByUsername(String username) {
        UserDTO user = readUserByUsername(username);
        return  user.getEmail();
    }

    //아이디 찾기
    public List<User> findId(String email, String name) {
    return  userRepository.findByEmailAndName(email, name);
    }
}