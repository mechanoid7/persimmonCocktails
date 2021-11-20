package com.example.persimmoncocktails.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestChangePasswordDataDto {
    @NotNull(message = "Person-ID should not be empty")
    Long personId;
    @NotNull(message = "New password should not be empty")
    String newPassword;
    @NotNull(message = "Old password should not be empty")
    String oldPassword;
}
