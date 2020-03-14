package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.Board;
import com.example.giveandtake.model.entity.Role;
import com.example.giveandtake.model.entity.User;
import lombok.*;

import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String username;

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
    private Set<Role> roles = new HashSet<>();

    public User toEntity() {
        return User.builder()
                .id(id)
                .username(username)
                .name(name)
                .password(password)
                .phone(phone)
                .email(email)
                .profileImage(profileImage)
                .roles(roles)
                .build();
    }

    @Builder
    public UserDTO(Long id, String name, String email, String password, String phone, String username, String profileImage, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.username = username;
        this.profileImage = profileImage;
        this.roles = roles;
    }


}
