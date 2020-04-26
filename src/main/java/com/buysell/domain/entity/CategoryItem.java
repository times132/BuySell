package com.buysell.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "categoryitems")
public class CategoryItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category; // 카테고리

    @Builder
    public CategoryItem(Long itemId, String itemName, Category category){
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
    }

}
