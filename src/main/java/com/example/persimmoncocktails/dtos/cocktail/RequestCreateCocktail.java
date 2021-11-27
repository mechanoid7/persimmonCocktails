package com.example.persimmoncocktails.dtos.cocktail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateCocktail {
    @NotNull
    private String name;
    private String description;
    private String dishType;
    private Long dishCategoryId;
    @NotNull
    private String receipt;
}

