package com.example.persimmoncocktails.mappers.cocktail;

import com.example.persimmoncocktails.models.cocktail.CocktailCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CocktailCategoryMapper implements RowMapper<CocktailCategory> {
    @Override
    public CocktailCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CocktailCategory(
                rs.getLong("dish_category_id"),
                rs.getString("name")
                );
    }
}
