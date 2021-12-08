package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.IngredientDao;
import com.example.persimmoncocktails.dtos.ingredient.IngredientNameDto;
import com.example.persimmoncocktails.dtos.ingredient.RequestIngredientDto;
import com.example.persimmoncocktails.dtos.ingredient.ResponseIngredientDto;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.exceptions.StateException;
import com.example.persimmoncocktails.models.ingredient.Ingredient;
import com.example.persimmoncocktails.models.ingredient.IngredientCategory;
import com.example.persimmoncocktails.models.ingredient.IngredientWithCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:var/general.properties")
public class IngredientService {

    private final IngredientDao ingredientDao;
    @Value("${amount_of_ingredients}")
    public Integer amountOfIngredientsToReturnWhileSearchingByPrefix;

    @Autowired
    public IngredientService(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }


    public IngredientWithCategory readIngredientId(Long ingredientId) {
        IngredientWithCategory ingredient = ingredientDao.read(ingredientId);
        if (ingredient == null) throw new NotFoundException("Ingredient");
        return ingredient;
    }

    public void updateName(Long ingredientId, String name) {
        if (!ingredientDao.existsById(ingredientId)) throw new NotFoundException("Ingredient");
        if (!AuthorizationService.nameIsValid(name)) throw new IncorrectNameFormat();
        Ingredient ingredient = ingredientDao.read(ingredientId).toIngredient();
        ingredient.setName(name);
        ingredientDao.update(ingredient);
    }

    public void updateIngredientCategory(Long ingredientId, Long ingredientCategoryId) {
        if (!ingredientDao.existsById(ingredientId)) throw new NotFoundException("Ingredient");
        if (!ingredientDao.existsCategoryById(ingredientCategoryId)) throw new NotFoundException("IngredientCategory");
        Ingredient ingredient = ingredientDao.read(ingredientId).toIngredient();
        ingredient.setIngredientCategoryId(ingredientCategoryId);
        ingredientDao.update(ingredient);
    }

    public void updatePhoto(Long ingredientId, Long photoId) {
        if (!ingredientDao.existsById(ingredientId)) throw new NotFoundException("Ingredient");
        // check photoId
        Ingredient ingredient = ingredientDao.read(ingredientId).toIngredient();
        ingredient.setPhotoId(photoId);
        ingredientDao.update(ingredient);
    }

    public IngredientWithCategory createIngredient(RequestIngredientDto requestIngredientDto) {
        if (requestIngredientDto.getIngredientCategoryId() != null &&
                !ingredientDao.existsCategoryById(requestIngredientDto.getIngredientCategoryId()))
            throw new NotFoundException("IngredientCategory");
        Ingredient ingredient = new Ingredient(null,
                requestIngredientDto.getName(),
                requestIngredientDto.getPhotoId(),
                requestIngredientDto.getIngredientCategoryId(),
                true
        );
        ingredientDao.create(ingredient);
        return ingredientDao.readByName(requestIngredientDto.getName());
    }

    public List<IngredientWithCategory> readAllIngredients() {
        return ingredientDao.readAllIngredients();
    }

    public List<IngredientCategory> readAllIngredientCategories() {
        return ingredientDao.readAllIngredientCategories();
    }

    public void deactivate(Long ingredientId) {
        if (!ingredientDao.existsById(ingredientId)) throw new NotFoundException("Ingredient");
        IngredientWithCategory ingredient = ingredientDao.read(ingredientId);
        if (!ingredient.isActive()) throw new StateException("This item is deactivated already");
        ingredient.setActive(false);
        ingredientDao.update(ingredient.toIngredient());
    }

    public void activate(Long ingredientId) {
        if (!ingredientDao.existsById(ingredientId)) throw new NotFoundException("Ingredient");
        IngredientWithCategory ingredient = ingredientDao.read(ingredientId);
        if (!ingredient.isActive()) throw new StateException("This item is activate already");
        ingredient.setActive(true);
        ingredientDao.update(ingredient.toIngredient());
    }

    public List<ResponseIngredientDto> readAllActiveIngredients() {
        return ingredientDao.readAllIngredients()
                .stream()
                .filter(IngredientWithCategory::isActive)
                .map(ResponseIngredientDto::toDto)
                .collect(Collectors.toList());

    }

    public ResponseIngredientDto readActiveIngredientId(Long ingredientId) {
        IngredientWithCategory ingredient = readIngredientId(ingredientId);
        if (!ingredient.isActive()) throw new NotFoundException("Ingredient");
        return ResponseIngredientDto.toDto(ingredient);
    }

    public List<IngredientNameDto> findActiveIngredientsByPrefix(String prefix) {
        return ingredientDao.findActiveIngredientsByPrefixLimitedAmount(prefix, amountOfIngredientsToReturnWhileSearchingByPrefix);
    }
}
