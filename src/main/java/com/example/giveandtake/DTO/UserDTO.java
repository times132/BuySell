package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.User;
import jdk.nashorn.internal.runtime.Debug;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    // Entity클래스가 변경되는 것을 막기위해 Controller에서 쓸 DTO 구현

    private String username;
    private String nickname;
    private String password;
    private String phone;
    private String email;




    public User toEntity(){
        return User.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .phone(phone)
                .email(email)
                .build();
    }
}
