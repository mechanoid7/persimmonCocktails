package com.example.persimmoncocktails.mappers;

import com.example.persimmoncocktails.models.Cocktail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CocktailMapper implements RowMapper<Cocktail> {
    @Override
    public Cocktail mapRow(ResultSet rs, int rowNum) throws SQLException {
        String description = rs.getString("description");
        if (rs.wasNull()) description = null;
        String type = rs.getString("type");
        if (rs.wasNull()) type = null;
        Long dishCategoryId = rs.getLong("dish_category_id");
        if (rs.wasNull()) dishCategoryId = 0L;
        String label = rs.getString("label");
        if (rs.wasNull()) label = null;
        Long likes = rs.getLong("likes");
        if (rs.wasNull()) likes = 0L;
        return new Cocktail(
                rs.getLong("dish_id"),
                rs.getString("name"),
                description,
                type,
                dishCategoryId,
                label,
                rs.getString("receipt"),
                likes,
                rs.getBoolean("is_active")
        );
    }
}
