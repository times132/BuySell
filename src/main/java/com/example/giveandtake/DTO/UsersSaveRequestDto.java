package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UsersSaveRequestDto {

    private String nickname;
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDateTime created_date;
    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .username(username)
                .email(email)
                .password(password)
                .phone(phone)
                .created_date(created_date)
                .build();
    }
}
