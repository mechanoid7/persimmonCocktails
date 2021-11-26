package com.example.persimmoncocktails.mappers;

import com.example.persimmoncocktails.models.kitchenware.KitchenwareCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KitchenwareCategoryMapper implements RowMapper<KitchenwareCategory> {
    @Override
    public KitchenwareCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new KitchenwareCategory(
                rs.getLong("kitchenware_category_id"),
                rs.getString("name"));
    }
}
