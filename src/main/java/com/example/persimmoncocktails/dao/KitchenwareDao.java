package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.models.Kitchenware;
import com.example.persimmoncocktails.models.Person;

public interface KitchenwareDao {
    boolean existsById(Long kitchenwareId);

    void create(Kitchenware kitchenware);

    Kitchenware read(Long kitchenwareId);

    Kitchenware readByName(String name);

    void update(Kitchenware kitchenware);

    void delete(Long kitchenwareId);
}
