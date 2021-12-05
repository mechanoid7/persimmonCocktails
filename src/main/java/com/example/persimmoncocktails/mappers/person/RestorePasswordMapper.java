package com.example.persimmoncocktails.mappers.person;

import com.example.persimmoncocktails.dtos.auth.RestorePasswordDataDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RestorePasswordMapper  implements RowMapper<RestorePasswordDataDto> {
    @Override
    public RestorePasswordDataDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        Timestamp timestamp = rs.getTimestamp("time");
        LocalDateTime localDateTime;
        if(rs.wasNull()) {
            localDateTime = null;
        } else localDateTime = timestamp.toLocalDateTime();

        return new RestorePasswordDataDto(rs.getLong("person_id"), localDateTime, rs.getString("id"));
    }
}
