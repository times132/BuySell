package com.buysell.domain.entity;

import com.buysell.domain.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;
    private String name;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String profileImage;
    private String provider;
    private Boolean activation;

    @PrePersist
    protected void prePersist(){
        if (this.profileImage == null) this.profileImage = "";
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRoles> roles  = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user", "boardFileList"})
    private List<Board> boardList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private List<Reply> replyList;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    private List<ChatUsers> chats;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private List<Like> likeList;

    @Builder //setter의 역할을 함, 어떤 값에 어느 것을 넣을지 쉽게 확인 가능
    public User(String username, String nickname, String name, String password, String phone, String email,Long id, String profileImage, String provider, Boolean activation, Set<UserRoles> roles, List<Like> likeList){
        this.username = username;
        this.nickname = nickname;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.id= id;
        this.profileImage = profileImage;
        this.provider = provider;
        this.activation = activation;
        this.likeList = likeList;
        this.roles = roles;
    }

}
