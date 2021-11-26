package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.CocktailDao;
import com.example.persimmoncocktails.dtos.cocktail.CocktailResponseDto;
import com.example.persimmoncocktails.dtos.cocktail.RequestCocktailUpdate;
import com.example.persimmoncocktails.dtos.cocktail.RequestCreateCocktail;
import com.example.persimmoncocktails.exceptions.DuplicateException;
import com.example.persimmoncocktails.exceptions.UnknownException;
import com.example.persimmoncocktails.mappers.CocktailMapper;
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
    @Value("${sql_cocktail_add_likes_counter}")
    private String sqlAddLikesCount;
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
    @Value("${sql_cocktail_get_labels}")
    private String sqlGetLabels;
    @Value("${sql_cocktail_set_labels}")
    private String sqlSetLabels;
    @Value("${sql_cocktails_like_exists}")
    private String sqlLikeExists;
    @Value("${sql_cocktails_dish_type_exists}")
    private String sqlDishTypeExists;
    @Value("${sql_cocktail_add_like_pair}")
    private String sqlAddLikePair;

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
    public CocktailResponseDto readById(Long personId) {
        return jdbcTemplate.queryForObject(sqlGetCocktailById, cocktailMapper, personId);
    }

    @Override
    public Boolean likeExists(Long personId, Long dishId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlLikeExists, Boolean.class, personId, dishId));
    }

    @Override
    public Boolean dishTypeExists(String dishType) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlDishTypeExists, Boolean.class, dishType));
    }

    @Override
    public void update(RequestCocktailUpdate cocktail) {
        jdbcTemplate.update(sqlUpdateCocktail, cocktail.getName(), cocktail.getDescription(), cocktail.getDishType(),
                cocktail.getDishCategoryId(), cocktail.getLabel(), cocktail.getReceipt(),
                cocktail.getIsActive(), cocktail.getDishId());
    }

    @Override
    public void deleteById(Long dishId) {
        jdbcTemplate.update(sqlDeleteCocktail, dishId);
    }

    @Override
    public void addLikeCount(Long dishId) {
        jdbcTemplate.update(sqlAddLikesCount, dishId, 1, dishId);
    }

    @Override
    public void addLikeTable(Long personId, Long dishId) {
        jdbcTemplate.update(sqlAddLikePair, personId, dishId);
    }

    @Override
    public Long getLikes(Long dishId) {
        return jdbcTemplate.queryForObject(sqlGetLikes, (rs, rowNum) -> rs.getLong("likes"), dishId);
    }

    @Override
    public List<CocktailResponseDto> searchFilterSort(String sqlRequest, Long pageNumber) {
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
    public List<CocktailResponseDto> getRawListOfCocktails(Long pageNumber) {
        return jdbcTemplate.query(sqlGetRawCocktails, cocktailMapper, pageNumber*cocktailsPerPage, cocktailsPerPage);
    }

    @Override
    public void addLabel(Long dishId, String label) {
        jdbcTemplate.update(sqlSetLabels, getLabels(dishId)+";"+label, dishId);
    }

    @Override
    public String getLabels(Long dishId) {
        return jdbcTemplate.queryForObject(sqlGetLabels, String.class, dishId);
    }

    @Override
    public void updateLabels(Long dishId, String label) {
        jdbcTemplate.update(sqlSetLabels, label, dishId);
    }
}
