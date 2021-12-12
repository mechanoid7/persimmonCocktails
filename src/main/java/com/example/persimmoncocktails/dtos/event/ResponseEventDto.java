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
public class ResponseEventDto {
    private Long eventId;
    private String name;
    private String description;
    private LocalDateTime datetime;
    private String place;
    private Long creatorId;
    private String creatorName;
}
