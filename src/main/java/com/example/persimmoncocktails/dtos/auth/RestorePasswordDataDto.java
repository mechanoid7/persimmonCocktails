package com.example.persimmoncocktails.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RestorePasswordDataDto {
    @NotNull(message = "Person-ID should not be empty")
    Long personId;
    LocalDateTime localDateTime;
    @NotNull(message = "ID should not be empty")
    String id;
}
