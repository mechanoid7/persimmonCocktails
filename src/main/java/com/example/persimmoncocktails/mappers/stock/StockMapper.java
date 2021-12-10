package com.example.persimmoncocktails.mappers.stock;

import com.example.persimmoncocktails.dtos.stock.RequestAddStockIngredientDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockMapper implements RowMapper<RequestAddStockIngredientDto> {
    @Override
    public RequestAddStockIngredientDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        int amount = rs.getInt("amount");
        if(rs.wasNull()) amount = 0;
        String measureType = rs.getString("measure_type");
        if(rs.wasNull()) measureType = "";
        return new RequestAddStockIngredientDto(rs.getLong("ingredient_id"),  measureType, amount);
    }
}
