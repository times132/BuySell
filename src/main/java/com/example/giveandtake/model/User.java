package com.example.giveandtake.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    private String nickname;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NotBlank
    @Size(max = 40)
    private String phone;

    @NotBlank
    @Email
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_date;

    @Builder
    public User(String username, String nickname, String password, String email, String phone){
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

}
