package com.example.persimmoncocktails.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RequestAddModeratorDataDto {
    @NotNull(message = "Name should not be empty")
    String name;
    @NotNull(message = "Email should not be empty")
    String email;
}
