package com.example.persimmoncocktails.dtos.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ImageResponseDto {
    private Long imageId;
    private String urlFull;
    private String urlMiddle;
    private String urlThumb;
}
