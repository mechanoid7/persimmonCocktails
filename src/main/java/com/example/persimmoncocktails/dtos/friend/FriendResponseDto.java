package com.example.persimmoncocktails.dtos.friend;


import com.example.persimmoncocktails.models.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendResponseDto {
    private Long personId;
    private String name;
    private Long photoId;
    private Long blogId;
}
