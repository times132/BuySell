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

    @Builder
    public Role(Long id, RoleName name){
        this.id = id;
        this.name = name;
    }
}
