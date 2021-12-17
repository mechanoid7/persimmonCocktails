package com.example.persimmoncocktails.dtos.kitchenware;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KitchenwareNameDto {
    Long kitchenwareId;
    String name;
}
