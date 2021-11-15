package com.example.persimmoncocktails.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSigninDataDto {
    @NotNull(message = "Password should not be empty")
    String password;
    @NotNull(message = "Email should not be empty")
    String email;
}
