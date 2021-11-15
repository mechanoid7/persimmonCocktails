package com.example.persimmoncocktails.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RequestCreatePasswordDataDto {
    @NotNull(message = "Request-ID should not be empty")
    String id;
    @NotNull(message = "Person-ID should not be empty")
    Long personId;
    @NotNull(message = "New password should not be empty")
    String newPassword;
}
