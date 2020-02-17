package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다. 이메일 형식으로 입력해주세요.")  //이메일 양식이어야 함
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;


    private String username;


    @NotBlank(message = "핸드폰번호는 필수 입력 값입니다.")
    private String phone;


    public User toEntity() {
        return User.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .phone(phone)
                .email(email)
                .build();
    }

    @Builder
    public UserDTO(String nickname, String email, String password, String phone, String username) {

        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.username = username;
    }
}
