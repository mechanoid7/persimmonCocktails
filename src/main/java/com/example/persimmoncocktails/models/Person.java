package com.example.persimmoncocktails.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Long personId;
    private String name;
    private String email;
    private String password;
    private Long photoId;
    private Long blogId;
    private Integer roleId;
}