package com.example.persimmoncocktails.mappers.ingredient;

import com.example.persimmoncocktails.dtos.ingredient.IngredientNameDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientNameMapper implements RowMapper<IngredientNameDto> {
    @Override
    public IngredientNameDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new IngredientNameDto(rs.getLong("ingridient_id"),
                rs.getString("name"));
    }
}
