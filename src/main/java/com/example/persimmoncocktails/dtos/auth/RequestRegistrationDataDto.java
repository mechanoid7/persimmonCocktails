package com.example.persimmoncocktails.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
