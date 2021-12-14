package com.example.persimmoncocktails.dtos.cocktail;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class RequestCocktailSelectDto {
    String name; // search
    String sortBy; // sort
    String dishType; // filter
    Long dishCategoryId; // filter
    Boolean sortDirection; // asc/desc
    List<Long> ingredients;
    Boolean showActive;
    Boolean showInactive;
    Boolean showMatchStock; // search cocktail in stock

    public Boolean isClear() {
        if(ingredients != null && !ingredients.isEmpty()) return false;
        return Stream.of(name, sortBy, dishType, dishCategoryId, showActive, showInactive, showMatchStock).allMatch(Objects::isNull); // all fields are null?
    }
}
