package com.example.persimmoncocktails.mappers.stock;

import com.example.persimmoncocktails.dtos.stock.StockInfoDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockIngredientMapper implements RowMapper<StockInfoDto> {
    @Override
    public StockInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new StockInfoDto(
                rs.getLong("person_id"),
                rs.getLong("ingredient_id"),
                rs.getString("name"),
                rs.getLong("photo_id"),
                rs.getLong("ingredient_category_id"),
                rs.getString("category_name"),
                rs.getInt("amount"),
                rs.getString("measure_type")
        );
    }
}
