package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.dtos.cocktail.*;
import com.example.persimmoncocktails.models.cocktail.CocktailCategory;

import java.util.List;

public interface CocktailDao {
    BasicCocktailDto create(RequestCreateCocktail cocktail);

    Boolean likeExists(Long personId, Long dishId);

    Boolean dishTypeExists(String dishType);

    BasicCocktailDto readById(Long dishId);

    BasicCocktailDto readByName(String name);

    void update(RequestCocktailUpdate cocktail);

    void deleteById(Long dishId);

    void addLikeCount(Long dishId);

    void addLikeTable(Long personId, Long dishId);

    Long getLikes(Long dishId);

    List<BasicCocktailDto> searchFilterSort(SqlSearchRequest searchRequest);

    Integer amountOfResults(SqlSearchRequest searchRequest);

    boolean cocktailIsActive(Long dishId);

    void deactivateCocktailById(Long dishId);

    void activateCocktailById(Long dishId);

    Boolean columnExists(String column);

    List<BasicCocktailDto> getRawListOfCocktails(Long pageNumber);

    void addLabel(Long dishId, String label);

    String getLabels(Long dishId);

    void updateLabels(Long dishId, String label);

    Boolean existsById(Long id);

    List<CocktailCategory> getCocktailCategories();

    FullCocktailDto getFullCocktailInfo(Long cocktailId);

    Boolean hasKitchenware(Long cocktailId, Long kitchenwareId);

    void addKitchenware(Long cocktailId, Long kitchenwareId);

    void updateImage(Long cocktailId, Long imageId);

    void removeKitchenware(Long cocktailId, Long kitchenwareId);

    Boolean hasIngredient(Long cocktailId, Long ingredientId);

    void addIngredient(Long cocktailId, Long ingredientId);

    void removeIngredient(Long cocktailId, Long ingredientId);

    Integer amountOfCocktails(boolean showActive, boolean showInactive);
}