package com.example.persimmoncocktails.dtos.cocktail;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CocktailsSearchResultDto {
    List<BasicCocktailDto> results;
    Integer amountOfPages;
}
