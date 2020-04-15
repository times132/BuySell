package com.example.giveandtake.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"category"})
    private List<CategoryItem> items  = new ArrayList<>();


    @Builder
    public Category(Long id, String name, List<CategoryItem> items){
        this.id = id;
        this.name = name;
        this.items = items;
    }
}
