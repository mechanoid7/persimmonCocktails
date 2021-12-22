package com.example.persimmoncocktails.mappers.kitchenware;

import com.example.persimmoncocktails.models.kitchenware.KitchenwareCategory;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareWithCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KitchenwareWithCategoryMapper implements RowMapper<KitchenwareWithCategory> {
    @Override
    public KitchenwareWithCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long photoId = rs.getLong("photo_id");
        if (rs.wasNull()) photoId = null;
        Long kitchenwareCategoryId = rs.getLong("kitchenware_category_id");
        if (rs.wasNull()) kitchenwareCategoryId = null;
        String kitchenwareCategoryName = rs.getString("kitchenware_category_name");
        if (rs.wasNull()) kitchenwareCategoryName = null;
        KitchenwareCategory kitchenwareCategory = null;
        if (kitchenwareCategoryId != null && kitchenwareCategoryName != null)
            kitchenwareCategory = new KitchenwareCategory(kitchenwareCategoryId, kitchenwareCategoryName);
        return new KitchenwareWithCategory(
                rs.getLong("kitchenware_id"),
                rs.getString("kitchenware_name"),
                photoId,
                kitchenwareCategory,
                rs.getBoolean("is_active")
        );
    }
}
