package com.example.persimmoncocktails.dtos.person;


import com.example.persimmoncocktails.models.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponseDto {
    private Long personId;
    private String name;
    private String email;
    private Long photoId;
    private Long blogId;
    private Integer roleId;
    private Boolean isActive;

    public static PersonResponseDto toDto(Person person){
        return PersonResponseDto.builder()
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
