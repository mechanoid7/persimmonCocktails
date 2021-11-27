package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.dtos.cocktail.CocktailResponseDto;
import com.example.persimmoncocktails.dtos.cocktail.RequestCocktailUpdate;
import com.example.persimmoncocktails.dtos.cocktail.RequestCreateCocktail;
import com.example.persimmoncocktails.models.cocktail.CocktailCategory;

import java.util.List;

public interface CocktailDao {
    void create(RequestCreateCocktail cocktail);

    Boolean likeExists(Long personId, Long dishId);

    Boolean dishTypeExists(String dishType);

    CocktailResponseDto readById(Long dishId);

    CocktailResponseDto readByName(String name);

    void update(RequestCocktailUpdate cocktail);

    void deleteById(Long dishId);

    void addLikeCount(Long dishId);

    void addLikeTable(Long personId, Long dishId);

    Long getLikes(Long dishId);

    List<CocktailResponseDto> searchFilterSort(String sqlRequest, Long pageNumber);

    boolean cocktailIsActive(Long dishId);

    void deactivateCocktailById(Long dishId);

    void activateCocktailById(Long dishId);

    Boolean columnExists(String column);

    List<CocktailResponseDto> getRawListOfCocktails(Long pageNumber);

    void addLabel(Long dishId, String label);

    String getLabels(Long dishId);

    void updateLabels(Long dishId, String label);

    Boolean existsById(Long id);

    List<CocktailCategory> getCocktailCategories();
}