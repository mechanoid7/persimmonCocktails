package com.example.persimmoncocktails.dtos.cocktail;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    Boolean showActive;
    Boolean showInactive;

    public Boolean isClear() {
        return Stream.of(name, sortBy, dishType, dishCategoryId).allMatch(Objects::isNull); // all fields are null?
    }
}
