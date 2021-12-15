package com.example.persimmoncocktails.mappers.cocktail;

import com.example.persimmoncocktails.dtos.cocktail.BasicCocktailDto;
import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import com.example.persimmoncocktails.services.CocktailService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CocktailMapper implements RowMapper<BasicCocktailDto> {
    @Override
    public BasicCocktailDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        String description = rs.getString("description");
        if (rs.wasNull()) description = null;
        String dishType = rs.getString("dish_type");
        if (rs.wasNull()) dishType = null;
        String dishCategoryName = rs.getString("dish_category_name");
        if (rs.wasNull()) dishCategoryName = null;
        Long dishCategoryId = rs.getLong("category_id");
        if (rs.wasNull()) dishCategoryId = null;
        String label = rs.getString("label");
        if (rs.wasNull()) label = null;
        List<String> labels = CocktailService.labelsFromString(label);
        Long likes = rs.getLong("likes");
        if (rs.wasNull()) likes = 0L;
        ImageResponseDto image = null;
        String url_full = rs.getString("url_full");
        String url_middle = rs.getString("url_middle");
        String url_thumb = rs.getString("url_thumb");
        Long photo_id = rs.getLong("photo_id");
        if(!rs.wasNull()) image = new ImageResponseDto(
                photo_id,
                url_full,
                url_middle,
                url_thumb
        );
        return new BasicCocktailDto(
                rs.getLong("dish_id"),
                image,
                rs.getString("name"),
                description,
                dishType,
                dishCategoryName,
                dishCategoryId,
                labels,
                rs.getString("receipt"),
                likes,
                rs.getBoolean("is_active")
        );
    }
}
