package com.example.giveandtake.DTO;

import com.example.giveandtake.model.entity.Category;
import com.example.giveandtake.model.entity.CategoryItem;
import com.example.giveandtake.model.entity.ChatRoom;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private List<CategoryItem> items  = new ArrayList<>();

    public Category toEntity(){
        return Category.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Builder
    public CategoryDTO(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
