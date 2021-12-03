package com.example.persimmoncocktails.mappers.ingredient;


import com.example.persimmoncocktails.models.ingredient.IngredientCategory;
import com.example.persimmoncocktails.models.ingredient.IngredientWithCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientWithCategoryMapper implements RowMapper<IngredientWithCategory> {
    @Override
    public IngredientWithCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long ingredientCategoryId = rs.getLong("ingridient_category_id");
        if (rs.wasNull()) ingredientCategoryId = null;
        Long photoId = rs.getLong("photo_id");
        if (rs.wasNull()) photoId = null;
        String ingredientCategoryName = rs.getString("ingridient_category_name");
        if (rs.wasNull()) ingredientCategoryName = null;
        IngredientCategory ingredientCategory = null;
        if (ingredientCategoryId != null && ingredientCategoryName != null)
            ingredientCategory = new IngredientCategory(ingredientCategoryId, ingredientCategoryName);
        return new IngredientWithCategory(
                rs.getLong("ingridient_id"),
                rs.getString("ingridient_name"),
                photoId,
                ingredientCategory,
                rs.getBoolean("is_active")
        );
    }
}
