package com.example.persimmoncocktails.mappers.stock;

import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import com.example.persimmoncocktails.dtos.stock.RequestStockIngredientIdDto;
import com.example.persimmoncocktails.dtos.stock.StockInfoDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockIngredientsIdMapper implements RowMapper<RequestStockIngredientIdDto> {
    @Override
    public RequestStockIngredientIdDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RequestStockIngredientIdDto(
                rs.getLong("ingridient_id")
        );
    }
}
