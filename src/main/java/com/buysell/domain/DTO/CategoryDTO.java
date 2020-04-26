package com.buysell.domain.DTO;

import com.buysell.domain.entity.Category;
import com.buysell.domain.entity.CategoryItem;
import lombok.*;

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
