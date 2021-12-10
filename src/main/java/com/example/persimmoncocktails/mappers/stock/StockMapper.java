package com.example.persimmoncocktails.mappers.stock;

import com.example.persimmoncocktails.dtos.stock.RequestAddStockIngredientDto;
import com.example.persimmoncocktails.dtos.stock.RequestStockSearchIngredientDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockMapper implements RowMapper<RequestStockSearchIngredientDto> {
    @Override
    public RequestStockSearchIngredientDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        String name = rs.getString("name");
        if(rs.wasNull()) name = "";
        return new RequestStockSearchIngredientDto(
                rs.getLong("ingredient_id"),
                rs.getString("name")
        );
    }
}
