package com.example.persimmoncocktails.mappers.ingredient;

import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import com.example.persimmoncocktails.dtos.ingredient.IngredientNameDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientNameMapper implements RowMapper<IngredientNameDto> {
    @Override
    public IngredientNameDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ImageResponseDto image = null;
        String urlFull = rs.getString("url_full");
        String urlMiddle = rs.getString("url_middle");
        String urlThumb = rs.getString("url_thumb");
        Long imageId = rs.getLong("photo_id");
        if(!rs.wasNull()){
            image = new ImageResponseDto(imageId, urlFull, urlMiddle, urlThumb);
        }
        else{
            imageId = null;
        }
        return new IngredientNameDto(rs.getLong("ingridient_id"),
                rs.getString("name"), image);
    }
}
