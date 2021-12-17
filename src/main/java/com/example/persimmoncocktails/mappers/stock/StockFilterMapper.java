package com.example.persimmoncocktails.mappers.stock;

import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
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
        if(rs.wasNull()) photoId = null;
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
        return new StockInfoDto(
                rs.getLong("person_id"),
                rs.getLong("ingridient_id"),
                name,
                photoId,
                rs.getLong("ingridient_category_id"),
                rs.getString("category_name"),
                amount,
                measureType,
                image
        );
    }
}