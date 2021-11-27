package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.models.kitchenware.KitchenwareCategory;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareWithCategory;
import com.example.persimmoncocktails.models.kitchenware.Kitchenware;

import java.util.List;

public interface KitchenwareDao {
    boolean existsById(Long kitchenwareId);

    void create(Kitchenware kitchenware);

    KitchenwareWithCategory read(Long kitchenwareId);

    KitchenwareWithCategory readByName(String name);

    void update(Kitchenware kitchenware);

    void delete(Long kitchenwareId);

    boolean existsCategoryById(Long kitchenwareCategoryId);

    List<KitchenwareWithCategory> readAllKitchenwares();

    List<KitchenwareWithCategory> readAllKitchenwaresUsedByCocktail(Long cocktailId);

    List<KitchenwareCategory> readAllKitchenwareCategories();
}
