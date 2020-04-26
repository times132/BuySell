package com.buysell.domain.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user_roles")
public class UserRoles{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"profileImage","replyList", "boardList", "password", "email", "phone", "roles", "provider", "name", "createdDate", "updatedDate", "activation","chats"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="name")
    @JsonIdentityReference(alwaysAsId=true)
    private Role role;

    @Builder
    public UserRoles(Long id, User user, Role role){
        this.id = id;
        this.user = user;
        this.role = role;
    }
}
