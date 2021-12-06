package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.KitchenwareDao;
import com.example.persimmoncocktails.exceptions.DuplicateException;
import com.example.persimmoncocktails.exceptions.UnknownException;
import com.example.persimmoncocktails.mappers.kitchenware.KitchenwareCategoryMapper;
import com.example.persimmoncocktails.mappers.kitchenware.KitchenwareWithCategoryMapper;
import com.example.persimmoncocktails.models.kitchenware.Kitchenware;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareCategory;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareWithCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/kitchenware_queries.properties")
public class KitchenwareDaoImpl implements KitchenwareDao {

    private final KitchenwareWithCategoryMapper kitchenwareWithCategoryMapper = new KitchenwareWithCategoryMapper();
    private final KitchenwareCategoryMapper kitchenwareCategoryMapper = new KitchenwareCategoryMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${sql_kitchenware_with_category_read_by_id}")
    private String sqlReadKitchenwareWithCategoryById;
    @Value("${sql_kitchenware_with_category_read_by_name}")
    private String sqlReadKitchenwareWithCategoryByName;
    @Value("${sql_kitchenware_update}")
    private String sqlUpdateKitchenware;
    @Value("${sql_kitchenware_delete}")
    private String sqlDeleteKitchenware;
    @Value("${sql_kitchenware_create}")
    private String sqlCreateKitchenware;
    @Value("${sql_kitchenware_with_such_id_exists}")
    private String sqlKitchenwareWithSuchIdExists;
    @Value("${sql_kitchenware_category_with_such_id_exists}")
    private String sqlKitchenwareCategoryWithSuchIdExists;
    @Value("${sql_kitchenwares_with_category}")
    private String sqlReadAllKitchenwares;
    @Value("${sql_kitchenware_category_read_all}")
    private String sqlReadAllKitchenwareCategories;
    @Value("${sql_kitchenwares_with_category_used_in_cocktail_by_id}")
    private String sqlReadAllKitchenwaresUsedByCocktail;

    @Override
    public boolean existsById(Long kitchenwareId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlKitchenwareWithSuchIdExists, Boolean.class, kitchenwareId));
    }

    @Override
    public void create(Kitchenware kitchenware) {
        try {
            jdbcTemplate.update(sqlCreateKitchenware, kitchenware.getName(),
                    kitchenware.getKitchenwareCategoryId(),
                    kitchenware.getPhotoId(),
                    kitchenware.isActive()
            );
        } catch (DuplicateKeyException e) {
            throw new DuplicateException("Kitchenware");
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public KitchenwareWithCategory read(Long kitchenwareId) {
        try {
            return jdbcTemplate.queryForObject(sqlReadKitchenwareWithCategoryById, kitchenwareWithCategoryMapper, kitchenwareId);
        } catch (EmptyResultDataAccessException emptyE) {
            return null;
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public KitchenwareWithCategory readByName(String name) {
        try {
            return jdbcTemplate.queryForObject(sqlReadKitchenwareWithCategoryByName, kitchenwareWithCategoryMapper, name);
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
            jdbcTemplate.update(sqlUpdateKitchenware,
                    kitchenware.getName(),
                    kitchenware.getKitchenwareCategoryId(),
                    kitchenware.getPhotoId(),
                    kitchenware.isActive(),
                    kitchenware.getKitchenwareId());
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

    @Override
    public boolean existsCategoryById(Long kitchenwareCategoryId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                sqlKitchenwareCategoryWithSuchIdExists, Boolean.class, kitchenwareCategoryId));
    }

    @Override
    public List<KitchenwareWithCategory> readAllKitchenwares() {
        return jdbcTemplate.query(sqlReadAllKitchenwares, kitchenwareWithCategoryMapper);
    }

    @Override
    public List<KitchenwareWithCategory> readAllKitchenwaresUsedByCocktail(Long cocktailId) {
        return jdbcTemplate.query(sqlReadAllKitchenwaresUsedByCocktail, kitchenwareWithCategoryMapper, cocktailId);
    }

    @Override
    public List<KitchenwareCategory> readAllKitchenwareCategories() {
        return jdbcTemplate.query(sqlReadAllKitchenwareCategories, kitchenwareCategoryMapper);
    }
}
