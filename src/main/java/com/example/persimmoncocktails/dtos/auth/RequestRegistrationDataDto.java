package com.example.persimmoncocktails.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRegistrationDataDto {
    @NotNull(message = "Name should not be empty")
    String name;
    @NotNull(message = "Password should not be empty")
    String password;
    @NotNull(message = "Email should not be empty")
    String email;
}
