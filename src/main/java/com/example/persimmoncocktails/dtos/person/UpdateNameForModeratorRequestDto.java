package com.example.persimmoncocktails.dtos.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UpdateNameForModeratorRequestDto {
    @NotNull
    Long personId;
    @NotNull
    String name;
}
