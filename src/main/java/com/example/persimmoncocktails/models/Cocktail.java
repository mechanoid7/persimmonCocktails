package com.example.persimmoncocktails.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cocktail {
    private Long dishId;
    private String name;
    private String description;
    private String dishType;
    private Long dishCategoryId;
    private String label;
    private String receipt;
    private Long likes;
    private Boolean isActive;
}

