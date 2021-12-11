package com.example.persimmoncocktails.dtos.cocktail;

import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicCocktailDto {
    private Long dishId;
    private ImageResponseDto image;
    private String name;
    private String description;
    private String dishType;
    private String dishCategoryName;
    private Long dishCategoryId;
    private List<String> labels;
    private String receipt;
    private Long likes;
    private Boolean isActive;
}

