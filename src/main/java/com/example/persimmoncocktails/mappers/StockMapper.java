package com.example.persimmoncocktails.mappers;

import com.example.persimmoncocktails.dtos.stock.StockIngredientsDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockMapper implements RowMapper<StockIngredientsDto> {
    @Override
    public StockIngredientsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        int amount = rs.getInt("amount");
        if(rs.wasNull()) amount = 0;
        String measureType = rs.getString("measure_type");
        if(rs.wasNull()) measureType = "";
        Long photoId = rs.getLong("photo_id");
        if(rs.wasNull()) photoId = 0l;
        return new StockIngredientsDto(rs.getLong("ingredient_id"), rs.getString("name"), measureType, amount, photoId);
    }
}
