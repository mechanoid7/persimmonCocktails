package com.example.persimmoncocktails.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RequestUpdatePhotoDataDto {
    @NotNull(message = "Person-ID should not be empty")
    Long personId;
    @NotNull(message = "Photo-ID should not be empty")
    Long photoId;
}
