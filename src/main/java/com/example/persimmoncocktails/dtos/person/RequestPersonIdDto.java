package com.example.persimmoncocktails.dtos.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPersonIdDto {
    @NotNull(message = "Person-ID should not be empty")
    Long personId;
}
