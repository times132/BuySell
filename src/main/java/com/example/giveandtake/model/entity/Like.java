package com.example.giveandtake.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@NoArgsConstructor
@Entity
@Getter
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bid")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="bid")
    @JsonIdentityReference(alwaysAsId=true)
    private Board board;


    @Builder
    public Like(Long id, User user, Board board){
        this.id = id;
        this.user = user;
        this.board = board;
    }
}
