package com.example.giveandtake.model.entity;

import com.example.giveandtake.domain.RoleName;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.TSFBuilder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    private RoleName name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserRoles> users;

    @Builder
    public Role(Long id, RoleName name, Set<UserRoles> users){
        this.id = id;
        this.name = name;
        this.users =users;
    }
}
