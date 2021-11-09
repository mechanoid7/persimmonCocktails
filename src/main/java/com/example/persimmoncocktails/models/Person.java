package com.example.persimmoncocktails.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
