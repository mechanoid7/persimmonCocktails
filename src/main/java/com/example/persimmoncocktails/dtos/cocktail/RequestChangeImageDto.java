package com.example.persimmoncocktails.dtos.cocktail;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class RequestChangeImageDto {
    @NotNull
    Long cocktailId;
    @NotNull
    Long imageId;
}
