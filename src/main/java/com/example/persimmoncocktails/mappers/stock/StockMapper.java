package com.example.persimmoncocktails.mappers.stock;

import com.example.persimmoncocktails.dtos.stock.RequestAddStockIngredientDto;
import com.example.persimmoncocktails.dtos.stock.RequestStockSearchIngredientDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockMapper implements RowMapper<RequestStockSearchIngredientDto> {
    @Override
    public RequestStockSearchIngredientDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        int amount = rs.getInt("amount");
        if(rs.wasNull()) amount = 1;
        String measureType = rs.getString("measure_type");
        if(rs.wasNull()) measureType = null;
        Long photoId = rs.getLong("photo_id");
        if(rs.wasNull()) photoId = null;
        return new RequestStockSearchIngredientDto(
                rs.getLong("person_id"),
                rs.getLong("ingridient_id"),
                rs.getString("name"),
                photoId,
                rs.getLong("ingridient_category_id"),
                rs.getString("category_name"),
                amount,
                measureType
        );
    }
}
