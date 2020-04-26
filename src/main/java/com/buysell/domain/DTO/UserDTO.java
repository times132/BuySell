package com.buysell.domain.DTO;

import com.buysell.domain.entity.UserRoles;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "ID 필수 입력 값입니다.")
    private String username;

    @NotBlank(message = "ID 필수 입력 값입니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다. 이메일 형식으로 입력해주세요.")  //이메일 양식이어야 함
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9]).{4,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 4자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @NotBlank(message = "이름 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "핸드폰번호는 필수 입력 값입니다.")
    private String phone;

    private String profileImage;
    private String provider;
    private Boolean activation;

    private Set<UserRoles> roles = new HashSet<>();

    @Builder
    public UserDTO(Long id, String username, String nickname, String email, String password, String name, String phone, String profileImage, String provider, Boolean activation, Set<UserRoles> roles) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.profileImage = profileImage;
        this.provider = provider;
        this.activation = activation;
        this.roles = roles;
    }
}
