package com.example.persimmoncocktails.dtos.kitchenware;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestKitchenwareDto {
    private String name;
    private Long kitchenwareCategoryId;
    private Long photoId;
}
