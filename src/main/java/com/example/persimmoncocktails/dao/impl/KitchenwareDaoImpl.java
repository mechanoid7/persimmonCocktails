package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.KitchenwareDao;
import com.example.persimmoncocktails.exceptions.DuplicateException;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.exceptions.UnknownException;
import com.example.persimmoncocktails.mappers.KitchenwareMapper;
import com.example.persimmoncocktails.models.Kitchenware;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/kitchenware_queries.properties")
public class KitchenwareDaoImpl implements KitchenwareDao {

    private final KitchenwareMapper kitchenwareMapper;
    private final JdbcTemplate jdbcTemplate;

    @Value("${sql_kitchenware_read_by_id}")
    private String sqlReadKitchenwareById;
    @Value("${sql_kitchenware_read_by_name}")
    private String sqlReadKitchenwareByName;
    @Value("${sql_kitchenware_update}")
    private String sqlUpdateKitchenware;
    @Value("${sql_kitchenware_delete}")
    private String sqlDeleteKitchenware;
    @Value("${sql_kitchenware_create}")
    private String sqlCreateKitchenware;
    @Value("${sql_kitchenware_with_such_id_exists}")
    private String sqlKitchenwareWithSuchIdExists;

    @Override
    public boolean existsById(Long kitchenwareId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlKitchenwareWithSuchIdExists, Boolean.class, kitchenwareId));
    }

    @Override
    public void create(Kitchenware kitchenware) {
        try {
            jdbcTemplate.update(sqlCreateKitchenware, kitchenware.getName(), kitchenware.getKitchenwareCategoryId(), kitchenware.getPhotoId());
        } catch (DuplicateKeyException e) {
            throw new DuplicateException("Kitchenware");
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public Kitchenware read(Long kitchenwareId) {
        try {
            return jdbcTemplate.queryForObject(sqlReadKitchenwareById, kitchenwareMapper, kitchenwareId);
        } catch (EmptyResultDataAccessException emptyE) {
            return null;
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public Kitchenware readByName(String name) {
        try {
            return jdbcTemplate.queryForObject(sqlReadKitchenwareByName, kitchenwareMapper, name);
        } catch (EmptyResultDataAccessException emptyE) {
            return null;
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void update(Kitchenware kitchenware) {
        // we should consider changing way to modify rows
        try {
            jdbcTemplate.update(sqlUpdateKitchenware, kitchenware.getName(), kitchenware.getKitchenwareCategoryId(), kitchenware.getPhotoId());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new NotFoundException("Kitchenware");
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void delete(Long kitchenwareId) {
        jdbcTemplate.update(sqlDeleteKitchenware, kitchenwareId);
    }
}
