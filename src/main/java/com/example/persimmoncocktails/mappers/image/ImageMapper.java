package com.example.persimmoncocktails.mappers.image;

import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageMapper implements RowMapper<ImageResponseDto> {
    @Override
    public ImageResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        String urlFull = rs.getString("url_full");
        if (rs.wasNull()) urlFull = null;
        String urlMiddle = rs.getString("url_middle");
        if (rs.wasNull()) urlMiddle = null;
        String urlThumb = rs.getString("url_thumb");
        if (rs.wasNull()) urlThumb = null;
        return new ImageResponseDto(rs.getLong("photo_id"), urlFull, urlMiddle, urlThumb);
    }
}
