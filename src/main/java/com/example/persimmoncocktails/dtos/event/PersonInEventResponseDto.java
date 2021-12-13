package com.example.persimmoncocktails.dtos.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonInEventResponseDto {
    private Long personId;
    private String name;
    private Long photoId;
    private Long blogId;
}
