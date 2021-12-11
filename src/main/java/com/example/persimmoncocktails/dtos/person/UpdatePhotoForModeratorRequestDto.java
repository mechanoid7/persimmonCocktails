package com.example.persimmoncocktails.dtos.person;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UpdatePhotoForModeratorRequestDto {
    @NotNull
    Long personId;
    @NotNull
    Long photoId;
}
