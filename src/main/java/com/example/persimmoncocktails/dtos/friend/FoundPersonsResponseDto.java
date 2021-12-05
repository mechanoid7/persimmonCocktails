package com.example.persimmoncocktails.dtos.friend;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoundPersonsResponseDto {
    private Long personId;
    private String name;
    private Long photoId;
    private Long blogId;
    private Boolean isInvited;
}
