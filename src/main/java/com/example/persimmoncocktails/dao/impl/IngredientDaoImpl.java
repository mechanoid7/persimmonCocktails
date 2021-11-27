package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.IngredientDao;
import com.example.persimmoncocktails.exceptions.DuplicateException;
import com.example.persimmoncocktails.exceptions.UnknownException;
import com.example.persimmoncocktails.mappers.IngredientCategoryMapper;
import com.example.persimmoncocktails.mappers.IngredientWithCategoryMapper;
import com.example.persimmoncocktails.models.ingredient.Ingredient;
import com.example.persimmoncocktails.models.ingredient.IngredientCategory;
import com.example.persimmoncocktails.models.ingredient.IngredientWithCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/ingredient_queries.properties")
public class IngredientDaoImpl implements IngredientDao {

    private final JdbcTemplate jdbcTemplate;
    private final IngredientWithCategoryMapper ingredientWithCategoryMapper = new IngredientWithCategoryMapper();
    private final IngredientCategoryMapper ingredientCategoryMapper = new IngredientCategoryMapper();

    @Value("${sql_ingredient_create}")
    private String sqlInsertNewIngredient;
    @Value("${sql_ingredient_update}")
    private String sqlUpdateIngredient;
    @Value("${sql_ingredient_delete}")
    private String sqlDeleteIngredient;
    @Value("${sql_ingredient_category_with_such_id_exists}")
    private String sqlIngredientCategoryWithSuchIdExists;
    @Value("${sql_ingredients_with_category}")
    private String sqlReadAllIngredients;
    @Value("${sql_ingredient_category_read_all}")
    private String sqlReadAllIngredientCategories;
    @Value("{sql_ingredient_with_such_id_exists}")
    private String sqlIngredientWithSuchIdExists;
    @Value("${sql_ingredient_with_category_read_by_id}")
    private String sqlReadIngredientWithCategoryById;
    @Value("${sql_ingredient_with_category_read_by_name}")
    private String sqlReadIngredientWithCategoryByName;
    @Value("${sql_ingredients_with_category_used_in_dish_by_id}")
    private String getSqlReadAllIngredientsUsedByCocktail;

    @Override
    public void create(Ingredient ingredient) {
        try {
            jdbcTemplate.update(sqlInsertNewIngredient, ingredient.getName(),
                    ingredient.getIngredientCategoryId(),
                    ingredient.getPhotoId(),
                    ingredient.isActive());
        } catch (DuplicateKeyException e) {
            throw new DuplicateException("Ingredient");
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }


    @Override
    public void update(Ingredient ingredient) {
        try {
            jdbcTemplate.update(sqlUpdateIngredient, ingredient.getName(),
                    ingredient.getIngredientCategoryId(),
                    ingredient.getPhotoId(),
                    ingredient.isActive(),
                    ingredient.getIngredientCategoryId());
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void delete(Long ingredientId) {
        jdbcTemplate.update(sqlDeleteIngredient, ingredientId);
    }

    @Override
    public boolean existsById(Long ingredientId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlIngredientWithSuchIdExists, Boolean.class, ingredientId));
    }

    @Override
    public IngredientWithCategory read(Long ingredientId) {
        try {
            return jdbcTemplate.queryForObject(sqlReadIngredientWithCategoryById, ingredientWithCategoryMapper, ingredientId);
        } catch (EmptyResultDataAccessException emptyE) {
            return null;
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public IngredientWithCategory readByName(String name) {
        try {
            return jdbcTemplate.queryForObject(sqlReadIngredientWithCategoryByName, ingredientWithCategoryMapper, name);
        } catch (EmptyResultDataAccessException emptyE) {
            return null;
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public boolean existsCategoryById(Long ingredientCategoryId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                sqlIngredientCategoryWithSuchIdExists, Boolean.class, ingredientCategoryId));
    }

    @Override
    public List<IngredientWithCategory> readAllIngredients() {
        return jdbcTemplate.query(sqlReadAllIngredients, ingredientWithCategoryMapper);
    }

    @Override
    public List<IngredientWithCategory> readAllIngredientsUsedByCocktail(Long cocktailId) {
        return jdbcTemplate.query(getSqlReadAllIngredientsUsedByCocktail, ingredientWithCategoryMapper, cocktailId);
    }

    @Override
    public List<IngredientCategory> readAllIngredientCategories() {
        return jdbcTemplate.query(sqlReadAllIngredientCategories, ingredientCategoryMapper);
    }
}
