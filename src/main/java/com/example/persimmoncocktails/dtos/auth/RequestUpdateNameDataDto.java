package com.example.persimmoncocktails.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RequestUpdateNameDataDto {
    @NotNull(message = "Person-ID should not be empty")
    Long personId;
    @NotNull(message = "Name should not be empty")
    String name;
}
