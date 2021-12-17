package com.example.persimmoncocktails.mappers.kitchenware;

import com.example.persimmoncocktails.dtos.ingredient.IngredientNameDto;
import com.example.persimmoncocktails.dtos.kitchenware.KitchenwareNameDto;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KitchenwareNameMapper implements RowMapper<KitchenwareNameDto> {

    @Override
    public KitchenwareNameDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new KitchenwareNameDto(rs.getLong("kitchenware_id"),
                rs.getString("name"));
    }
}
