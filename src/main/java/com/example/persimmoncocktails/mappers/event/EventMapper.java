package com.example.persimmoncocktails.mappers.event;

import com.example.persimmoncocktails.dtos.event.ResponseEventDto;
import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventMapper implements RowMapper<ResponseEventDto> {
    @Override
    public ResponseEventDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        String urlFull = rs.getString("url_full");
        if (rs.wasNull()) urlFull = null;
        String urlMiddle = rs.getString("url_middle");
        if (rs.wasNull()) urlMiddle = null;
        String urlThumb = rs.getString("url_thumb");
        if (rs.wasNull()) urlThumb = null;
        return new ResponseEventDto(rs.getLong("photo_id"), urlFull, urlMiddle, urlThumb);
    }
}
