package com.example.persimmoncocktails.dtos.cocktail;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestCocktailSelectDto {
//    @NotNull(message = "New password should not be empty")
    String name; // search
    String sortBy; // sort
    String dishType; // filter
    Long dishCategoryId; // filter
}
