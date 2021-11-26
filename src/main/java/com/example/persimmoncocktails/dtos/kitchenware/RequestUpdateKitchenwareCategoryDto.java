package com.example.persimmoncocktails.dtos.kitchenware;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateKitchenwareCategoryDto {
    Long kitchenwareId;
    Long kitchenwareCategoryId;
}
