package com.example.persimmoncocktails.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Kitchenware {
    private Long kitchenwareId;
    private String name;
    private Long kitchenwareCategoryId;
    private Long photoId;
}
