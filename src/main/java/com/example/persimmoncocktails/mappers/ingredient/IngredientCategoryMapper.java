package com.example.persimmoncocktails.mappers.ingredient;

import com.example.persimmoncocktails.models.ingredient.IngredientCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientCategoryMapper implements RowMapper<IngredientCategory> {
    @Override
    public IngredientCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new IngredientCategory(
                rs.getLong("ingridient_category_id"),
                rs.getString("name"));
    }
}
