package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.CocktailDao;
import com.example.persimmoncocktails.dtos.cocktail.RequestCreateCocktail;
import com.example.persimmoncocktails.exceptions.DuplicateException;
import com.example.persimmoncocktails.exceptions.UnknownException;
import com.example.persimmoncocktails.mappers.CocktailMapper;
import com.example.persimmoncocktails.models.Cocktail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PropertySources({
        @PropertySource("classpath:sql/cocktail_queries.properties"),
        @PropertySource("classpath:var/general.properties")
})
@RequiredArgsConstructor
public class CocktailDaoImpl implements CocktailDao {

    @Value("${sql_cocktail_add}")
    private String sqlCocktailAdd;
    @Value("${sql_cocktail_get_by_id}")
    private String sqlGetCocktailById;
    @Value("${sql_cocktail_update}")
    private String sqlUpdateCocktail;
    @Value("${sql_cocktail_delete}")
    private String sqlDeleteCocktail;
    @Value("${sql_cocktail_add_likes}")
    private String sqlAddLikes;
    @Value("${sql_cocktail_get_likes_by_id}")
    private String sqlGetLikes;
    @Value("${sql_cocktail_set_likes_by_id}")
    private String sqlSetLikes;
    @Value("${sql_cocktail_is_active}")
    private String sqlCocktailIsActive;
    @Value("${sql_cocktail_activate_cocktail}")
    private String sqlActivateCocktail;
    @Value("${sql_cocktail_deactivate_cocktail}")
    private String sqlDeactivateCocktail;
    @Value("${sql_cocktail_column_exists}")
    private String sqlColumnExists;
    @Value("${sql_cocktail_get_raw_cocktails}")
    private String sqlGetRawCocktails;

    @Value("${number_of_cocktails_per_page}")
    private Long cocktailsPerPage;

    private final JdbcTemplate jdbcTemplate;
    private final CocktailMapper cocktailMapper = new CocktailMapper();

    @Override
    public void create(RequestCreateCocktail cocktail) {
        try {
            jdbcTemplate.update(sqlCocktailAdd, cocktail.getName(), cocktail.getDescription(),cocktail.getDishType(),
                    cocktail.getDishCategoryId(), cocktail.getLabel(), cocktail.getReceipt(), 0, true);
        } catch (DuplicateKeyException e) {
            throw new DuplicateException("Person");
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public Cocktail readById(Long personId) {
        return jdbcTemplate.queryForObject(sqlGetCocktailById, cocktailMapper, personId);
    }

    @Override
    public void update(Cocktail cocktail) {
        jdbcTemplate.update(sqlUpdateCocktail, cocktail.getName(), cocktail.getDescription(), cocktail.getDishType(),
                cocktail.getDishCategoryId(), cocktail.getLabel(), cocktail.getReceipt(),
                cocktail.getIsActive(), cocktail.getDishId());
    }

    @Override
    public void deleteById(Long dishId) {
        jdbcTemplate.update(sqlDeleteCocktail, dishId);
    }

    @Override
    public void addLikes(Long dishId, Long likeNumber) {
        jdbcTemplate.update(sqlAddLikes, dishId, likeNumber, dishId);
    }

    @Override
    public Long getLikes(Long dishId) {
        return jdbcTemplate.queryForObject(sqlGetLikes, (rs, rowNum) -> rs.getLong("likes"), dishId);
    }

    @Override
    public void setLikes(Long dishId, Long likeNumber) {
        jdbcTemplate.update(sqlSetLikes, likeNumber, dishId);
    }

    @Override
    public List<Cocktail> searchFilterSort(String sqlRequest, Long pageNumber) {
        return jdbcTemplate.query(sqlRequest, cocktailMapper, pageNumber*cocktailsPerPage, cocktailsPerPage);
    }

    @Override
    public boolean cocktailIsActive(Long dishId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlCocktailIsActive, Boolean.class, dishId));
    }

    @Override
    public void deactivateCocktailById(Long dishId) {
        jdbcTemplate.update(sqlDeactivateCocktail, dishId);
    }

    @Override
    public void activateCocktailById(Long dishId) {
        jdbcTemplate.update(sqlActivateCocktail, dishId);
    }

    @Override
    public Boolean columnExists(String column) {
        return jdbcTemplate.queryForObject(sqlColumnExists, Boolean.class, column);
    }

    @Override
    public List<Cocktail> getRawListOfCocktails(Long pageNumber) {
        return jdbcTemplate.query(sqlGetRawCocktails, cocktailMapper, pageNumber*cocktailsPerPage, cocktailsPerPage);
    }
}
