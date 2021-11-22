package com.example.persimmoncocktails.mappers;

import com.example.persimmoncocktails.models.Kitchenware;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KitchenwareMapper implements RowMapper<Kitchenware> {
    @Override
    public Kitchenware mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long photoId = rs.getLong("photo_id");
        if(rs.wasNull()) photoId = null;
        Long kitchenwareCategoryId = rs.getLong("kitchenware_category_id");
        if(rs.wasNull()) kitchenwareCategoryId = null;
        return new Kitchenware(
                rs.getLong("kitchenware_id"),
                rs.getString("name"),
                photoId,
                kitchenwareCategoryId
        );
    }
}
