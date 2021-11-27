package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.CocktailDao;
import com.example.persimmoncocktails.dtos.cocktail.CocktailResponseDto;
import com.example.persimmoncocktails.dtos.cocktail.RequestCocktailUpdate;
import com.example.persimmoncocktails.dtos.cocktail.RequestCreateCocktail;
import com.example.persimmoncocktails.exceptions.DuplicateException;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.exceptions.UnknownException;
import com.example.persimmoncocktails.mappers.CocktailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    private final JdbcTemplate jdbcTemplate;
    private final CocktailMapper cocktailMapper = new CocktailMapper();
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
    @Value("${sql_dish_exists}")
    private String sqlDishExists;
    @Value("${number_of_cocktails_per_page}")
    private Long cocktailsPerPage;


    @Override
    public void create(RequestCreateCocktail cocktail) {
        try {
            jdbcTemplate.update(sqlCocktailAdd, cocktail.getName(), cocktail.getDescription(), cocktail.getDishType(),
                    cocktail.getDishCategoryId(), "", cocktail.getReceipt(), 0, true);
        } catch (DuplicateKeyException e) {
            throw new DuplicateException("Cocktail");
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public CocktailResponseDto readById(Long dishId) {
        try {
            return jdbcTemplate.queryForObject(sqlGetCocktailById, cocktailMapper, dishId);
        } catch (EmptyResultDataAccessException emptyE) {
            return null;
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
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
        try {
            jdbcTemplate.update(sqlUpdateCocktail, cocktail.getName(), cocktail.getDescription(), cocktail.getDishType(),
                    cocktail.getDishCategoryId(), cocktail.getReceipt(),
                    cocktail.getIsActive(), cocktail.getDishId());
        } catch (EmptyResultDataAccessException emptyE) {
            throw new NotFoundException("Cocktail");
        } catch (DataIntegrityViolationException dataIntegrityViolationException){
            throw new NotFoundException("Cocktail category");
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void deleteById(Long dishId) {
        try {
            jdbcTemplate.update(sqlDeleteCocktail, dishId);
        } catch (EmptyResultDataAccessException emptyE) {
            throw new NotFoundException("Cocktail");
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void addLikeCount(Long dishId) {
        try {
            jdbcTemplate.update(sqlAddLikesCount, dishId, 1, dishId);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new NotFoundException("Cocktail");
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void addLikeTable(Long personId, Long dishId) {
        try {
            jdbcTemplate.update(sqlAddLikePair, personId, dishId);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new NotFoundException("Cocktail");
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public Long getLikes(Long dishId) {
        try {
            return jdbcTemplate.queryForObject(sqlGetLikes, (rs, rowNum) -> rs.getLong("likes"), dishId);
        } catch (EmptyResultDataAccessException emptyE) {
            throw new NotFoundException("Cocktail");
        } catch (DataAccessException dataAccessException) {
            dataAccessException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public List<CocktailResponseDto> searchFilterSort(String sqlRequest, Long pageNumber) {
        return jdbcTemplate.query(sqlRequest, cocktailMapper, pageNumber * cocktailsPerPage, cocktailsPerPage);
    }

    @Override
    public boolean cocktailIsActive(Long dishId) {
        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlCocktailIsActive, Boolean.class, dishId));
        } catch (EmptyResultDataAccessException emptyE) {
            throw new NotFoundException("Cocktail");
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void deactivateCocktailById(Long dishId) {
        try {
            jdbcTemplate.update(sqlDeactivateCocktail, dishId);
        } catch (EmptyResultDataAccessException emptyE) {
            throw new NotFoundException("Cocktail");
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void activateCocktailById(Long dishId) {
        try {
            jdbcTemplate.update(sqlActivateCocktail, dishId);
        } catch (EmptyResultDataAccessException emptyE) {
            throw new NotFoundException("Cocktail");
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public Boolean columnExists(String column) {
        return jdbcTemplate.queryForObject(sqlColumnExists, Boolean.class, column);
    }

    @Override
    public List<CocktailResponseDto> getRawListOfCocktails(Long pageNumber) {
        return jdbcTemplate.query(sqlGetRawCocktails, cocktailMapper, pageNumber * cocktailsPerPage, cocktailsPerPage);
    }

    @Override
    public void addLabel(Long dishId, String label) {
        try {
            jdbcTemplate.update(sqlSetLabels, getLabels(dishId) + ";" + label, dishId);
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public String getLabels(Long dishId) {
        try {
            return jdbcTemplate.queryForObject(sqlGetLabels, String.class, dishId);
        } catch (EmptyResultDataAccessException emptyE) {
            throw new NotFoundException("Cocktail");
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void updateLabels(Long dishId, String label) {
        jdbcTemplate.update(sqlSetLabels, label, dishId);
    }

    @Override
    public Boolean existsById(Long dishId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlDishExists, Boolean.class, dishId));
    }
}
