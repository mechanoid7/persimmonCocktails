package com.example.persimmoncocktails.dtos.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateEventDto {
    @NotNull
    private String name;
    private String description;
    private LocalDateTime datetime;
    private String place;
}
