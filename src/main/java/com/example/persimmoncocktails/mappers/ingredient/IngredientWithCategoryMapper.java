package com.example.persimmoncocktails.mappers.ingredient;


import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
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

        ImageResponseDto image = null;
        String urlFull = rs.getString("url_full");
        String urlMiddle = rs.getString("url_middle");
        String urlThumb = rs.getString("url_thumb");
        Long imageId = rs.getLong("photo_id");
        if (!rs.wasNull()) {
            image = new ImageResponseDto(imageId, urlFull, urlMiddle, urlThumb);
        } else {
            imageId = null;
        }

        String ingredientCategoryName = rs.getString("ingridient_category_name");
        if (rs.wasNull()) ingredientCategoryName = null;
        IngredientCategory ingredientCategory = null;
        if (ingredientCategoryId != null && ingredientCategoryName != null)
            ingredientCategory = new IngredientCategory(ingredientCategoryId, ingredientCategoryName);
        return new IngredientWithCategory(
                rs.getLong("ingridient_id"),
                rs.getString("ingridient_name"),
                image,
                imageId,
                ingredientCategory,
                rs.getBoolean("is_active")
        );
    }
}
