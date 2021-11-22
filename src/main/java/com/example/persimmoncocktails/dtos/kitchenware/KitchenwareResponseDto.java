package com.example.persimmoncocktails.dtos.kitchenware;

import com.example.persimmoncocktails.models.Kitchenware;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenwareResponseDto {
    private Long kitchenwareId;
    private String name;
    private Long kitchenwareCategoryId;
    private Long photoId;

    public static KitchenwareResponseDto toDto(Kitchenware kitchenware) {
        return new KitchenwareResponseDto(
                kitchenware.getKitchenwareId(),
                kitchenware.getName(),
                kitchenware.getKitchenwareCategoryId(),
                kitchenware.getPhotoId()
        );
    }
}
