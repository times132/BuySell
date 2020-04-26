package com.buysell.domain.DTO;

import com.buysell.domain.entity.Category;
import com.buysell.domain.entity.CategoryItem;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryItemDTO {


    private Long itemId;
    private String itemName;
    private Category category; // 카테고리

    public CategoryItem toEntity(){
        return CategoryItem.builder()
                .itemId(itemId)
                .itemName(itemName)
                .category(category)
                .build();
    }
    @Builder
    public CategoryItemDTO(Long itemId,String itemName, Category category){
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
    }
}
