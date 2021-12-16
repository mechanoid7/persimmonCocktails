package com.example.persimmoncocktails.mappers.stock;

import com.example.persimmoncocktails.dtos.stock.RequestStockIngredientSelectDto;
import com.example.persimmoncocktails.dtos.stock.StockInfoDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockFilterMapper implements RowMapper<StockInfoDto> {
    @Override
    public StockInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        String name = rs.getString("name");
        if(rs.wasNull()) name = "";
        int amount = rs.getInt("amount");
        if(rs.wasNull()) amount = 1;
        String measureType = rs.getString("measure_type");
        if(rs.wasNull()) measureType = "";
        Long photoId = rs.getLong("photo_id");
        if(rs.wasNull()) photoId = 1l;
        return new StockInfoDto(
                rs.getLong("person_id"),
                rs.getLong("ingredient_id"),
                name,
                photoId,
                rs.getLong("ingredient_category_id"),
                rs.getString("category_name"),
                amount,
                measureType
        );
    }
}