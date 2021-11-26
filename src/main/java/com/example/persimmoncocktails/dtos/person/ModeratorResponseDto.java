package com.example.persimmoncocktails.dtos.person;

import com.example.persimmoncocktails.models.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ModeratorResponseDto {
    private Long personId;
    private String name;
    private String email;
    private Long photoId;
    private Long blogId;
    private Integer roleId;
    private Boolean isActive;

    public static ModeratorResponseDto toDto(Person person){
        return ModeratorResponseDto.builder()
                .personId(person.getPersonId())
                .blogId(person.getBlogId())
                .email(person.getEmail())
                .name(person.getName())
                .photoId(person.getPhotoId())
                .roleId(person.getRoleId())
                .isActive(person.getIsActive())
                .build();
    }
}
