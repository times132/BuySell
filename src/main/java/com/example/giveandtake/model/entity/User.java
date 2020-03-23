package com.example.giveandtake.model.entity;

import com.example.giveandtake.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user", "boardFileList"})
    private List<Board> boardList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private List<Reply> replyList;

    @Builder //setter의 역할을 함, 어떤 값에 어느 것을 넣을지 쉽게 확인 가능
    public User(String username, String nickname, String name, String password, String phone, String email,Long id, String profileImage, String provider, Boolean activation, Set<Role> roles){
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
        this.roles = roles;
    }

}
