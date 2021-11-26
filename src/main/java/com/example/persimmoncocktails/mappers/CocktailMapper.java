package com.example.persimmoncocktails.mappers;

import com.example.persimmoncocktails.dtos.cocktail.CocktailResponseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CocktailMapper implements RowMapper<CocktailResponseDto> {
    @Override
    public CocktailResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        String description = rs.getString("description");
        if (rs.wasNull()) description = null;
        String dishType = rs.getString("dish_type");
        if (rs.wasNull()) dishType = null;
        String dishCategoryName = rs.getString("dish_name");
        if (rs.wasNull()) dishCategoryName = null;
        Long dishCategoryId = rs.getLong("category_id");
        if (rs.wasNull()) dishCategoryName = null;
        String label = rs.getString("label");
        if (rs.wasNull()) label = null;
        Long likes = rs.getLong("likes");
        if (rs.wasNull()) likes = 0L;
        return new CocktailResponseDto(
                rs.getLong("dish_id"),
                rs.getString("name"),
                description,
                dishType,
                dishCategoryName,
                dishCategoryId,
                label,
                rs.getString("receipt"),
                likes,
                rs.getBoolean("is_active")
        );
    }
}
