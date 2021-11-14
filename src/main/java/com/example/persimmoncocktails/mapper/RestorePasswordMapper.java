package com.example.persimmoncocktails.mapper;

import com.example.persimmoncocktails.dtos.auth.RestorePasswordDataDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RestorePasswordMapper  implements RowMapper<RestorePasswordDataDto> {
    @Override
    public RestorePasswordDataDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocalDateTime localDateTime = rs.getTimestamp("time").toLocalDateTime();
        if(rs.wasNull()) localDateTime = null;
        return new RestorePasswordDataDto(rs.getLong("person_id"), localDateTime, rs.getString("id"));
    }
}
