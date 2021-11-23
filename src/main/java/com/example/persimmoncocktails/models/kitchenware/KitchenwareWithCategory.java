package com.example.persimmoncocktails.models.kitchenware;

import com.example.persimmoncocktails.models.kitchenware.KitchenwareCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KitchenwareWithCategory {
    private Long kitchenwareId;
    private String name;
    private Long photoId;
    private KitchenwareCategory category;

    public Kitchenware toKitchenware(){
        return new Kitchenware(
                kitchenwareId,
                name,
                photoId,
                category == null ? null : category.getKitchenwareCategoryId()
        );
    }
}
