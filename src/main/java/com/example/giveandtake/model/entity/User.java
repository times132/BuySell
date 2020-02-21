package com.example.giveandtake.model.entity;

import com.example.giveandtake.model.audit.DateAudit;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "users")
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String nickname;

    private String password;

    private String phone;

    private String email;

    @Builder //setter의 역할을 함, 어떤 값에 어느 것을 넣을지 쉽게 확인 가능
    public User(String username, String nickname, String password, String phone, String email,Long id){
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.id= id;
    }

}
