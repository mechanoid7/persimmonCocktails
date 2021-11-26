package com.example.persimmoncocktails.dtos.kitchenware;

import com.example.persimmoncocktails.models.kitchenware.KitchenwareCategory;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareWithCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResponseKitchenwareDto {
    private Long kitchenwareId;
    private String name;
    private Long photoId;
    private KitchenwareCategory category;

    public static ResponseKitchenwareDto toDto(KitchenwareWithCategory kitchenware){
        return new ResponseKitchenwareDto(
                kitchenware.getKitchenwareId(),
                kitchenware.getName(),
                kitchenware.getPhotoId(),
                kitchenware.getCategory()
        );
    }
}
