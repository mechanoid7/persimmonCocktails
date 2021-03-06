package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.CocktailDao;
import com.example.persimmoncocktails.dao.IngredientDao;
import com.example.persimmoncocktails.dao.KitchenwareDao;
import com.example.persimmoncocktails.dtos.cocktail.*;
import com.example.persimmoncocktails.exceptions.DuplicateException;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.exceptions.UnknownException;
import com.example.persimmoncocktails.mappers.cocktail.CocktailCategoryMapper;
import com.example.persimmoncocktails.mappers.cocktail.CocktailMapper;
import com.example.persimmoncocktails.models.cocktail.CocktailCategory;
import com.example.persimmoncocktails.models.ingredient.IngredientWithCategory;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareWithCategory;
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
    private final CocktailCategoryMapper cocktailCategoryMapper = new CocktailCategoryMapper();
    private final KitchenwareDao kitchenwareDao;
    private final IngredientDao ingredientDao;

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
    @Value("${sql_cocktail_get_by_name}")
    private String sqlGetByName;
    @Value("${sql_cocktail_category_get_all}")
    private String sqlGetCocktailCategories;
    @Value("${sql_cocktail_kitchenware_exist}")
    private String sqlKitchenwareExists;
    @Value("${sql_cocktail_kitchenware_add}")
    private String sqlAddKitchenware;
    @Value("${sql_cocktail_kitchenware_remove}")
    private String sqlRemoveKitchenware;
    @Value("${sql_cocktail_ingredient_exist}")
    private String sqlIngredientExists;
    @Value("${sql_cocktail_ingredient_add}")
    private String sqlAddIngredient;
    @Value("${sql_cocktail_ingredient_remove}")
    private String sqlRemoveIngredient;
    @Value("${sql_cocktail_update_image_id}")
    private String sqlUpdatePhoto;
    @Value("${sql_amount_of_cocktails}")
    private String sqlAmountOfCocktails;
    @Value("${number_of_cocktails_per_page}")
    private Long cocktailsPerPage;


    @Override
    public BasicCocktailDto create(RequestCreateCocktail cocktail) {
        try {
            try {
                jdbcTemplate.update(sqlCocktailAdd, cocktail.getName(), cocktail.getDescription(),
                        cocktail.getDishType(), cocktail.getDishCategoryId(), "", cocktail.getReceipt(),
                        0, true, cocktail.getPhotoId());
            } catch (DuplicateKeyException e) {
                throw new DuplicateException("Cocktail", "name");
            } catch (DataIntegrityViolationException dataIntegrityViolationException) {
                throw new NotFoundException("Cocktail category");
            }

            BasicCocktailDto savedCocktail = readByName(cocktail.getName());

            //TODO: optimize queries for adding kitchenware, ingredients
            for (Long kitchenwareId : cocktail.getUniqueKitchenwareIds()) {
                addKitchenware(savedCocktail.getDishId(), kitchenwareId);
            }
            for (Long ingredientId : cocktail.getUniqueIngredientIds()) {
                addIngredient(savedCocktail.getDishId(), ingredientId);
            }
            return savedCocktail;
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public BasicCocktailDto readById(Long dishId) {
        try {
            return jdbcTemplate.queryForObject(sqlGetCocktailById, cocktailMapper, dishId);
        } catch (EmptyResultDataAccessException emptyE) {
            return null;
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public BasicCocktailDto readByName(String name) {
        try {
            return jdbcTemplate.queryForObject(sqlGetByName, cocktailMapper, name);
        } catch (EmptyResultDataAccessException emptyE) {
            return null;
        } catch (DataAccessException rootException) {
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
                    cocktail.getIsActive(), cocktail.getPhotoId(),
                    cocktail.getDishId());
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new DuplicateException("Cocktail", "name");
        } catch (EmptyResultDataAccessException emptyE) {
            throw new NotFoundException("Cocktail");
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
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
    public List<BasicCocktailDto> searchFilterSort(SqlSearchRequest searchRequest) {
        return jdbcTemplate.query(searchRequest.getRequest(), cocktailMapper, searchRequest.getValues().toArray());
    }

    @Override
    public Integer amountOfResults(SqlSearchRequest searchRequest) {
        try {
            return jdbcTemplate.queryForObject(searchRequest.getRequest(), Integer.class, searchRequest.getValues().toArray());
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public boolean cocktailIsActive(Long dishId) {
        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlCocktailIsActive, Boolean.class, dishId));
        } catch (EmptyResultDataAccessException emptyE) {
            throw new NotFoundException("Cocktail");
        } catch (DataAccessException rootException) {
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
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public Boolean columnExists(String column) {
        return jdbcTemplate.queryForObject(sqlColumnExists, Boolean.class, column);
    }

    @Override
    public List<BasicCocktailDto> getRawListOfCocktails(Long pageNumber) {
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

    @Override
    public List<CocktailCategory> getCocktailCategories() {
        return jdbcTemplate.query(sqlGetCocktailCategories, cocktailCategoryMapper);
    }

    @Override
    public FullCocktailDto getFullCocktailInfo(Long cocktailId) {
        BasicCocktailDto cocktail = readById(cocktailId);
        if (cocktail == null) return null;
        List<KitchenwareWithCategory> kitchenwareList = kitchenwareDao.readAllKitchenwaresUsedByCocktail(cocktailId);
        List<IngredientWithCategory> ingredientList = ingredientDao.readAllIngredientsUsedByCocktail(cocktailId);
        return new FullCocktailDto(
                cocktail,
                kitchenwareList,
                ingredientList
        );
    }

    @Override
    public Boolean hasKitchenware(Long cocktailId, Long kitchenwareId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlKitchenwareExists, Boolean.class, cocktailId, kitchenwareId));
    }

    @Override
    public void addKitchenware(Long cocktailId, Long kitchenwareId) {
        try {
            jdbcTemplate.update(sqlAddKitchenware, cocktailId, kitchenwareId);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new NotFoundException("Cocktail or Kitchenware");
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void updateImage(Long cocktailId, Long imageId) {
        try {
            jdbcTemplate.update(sqlUpdatePhoto, imageId, cocktailId);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new NotFoundException("Cocktail");
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void removeKitchenware(Long cocktailId, Long kitchenwareId) {
        try {
            jdbcTemplate.update(sqlRemoveKitchenware, cocktailId, kitchenwareId);
        } catch (EmptyResultDataAccessException emptyE) {
            throw new NotFoundException("Cocktail-Kitchenware");
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public Boolean hasIngredient(Long cocktailId, Long ingredientId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlIngredientExists, Boolean.class, cocktailId, ingredientId));
    }

    @Override
    public void addIngredient(Long cocktailId, Long ingredientId) {
        try {
            jdbcTemplate.update(sqlAddIngredient, cocktailId, ingredientId);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new NotFoundException("Cocktail or Ingredient");
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void removeIngredient(Long cocktailId, Long ingredientId) {
        try {
            jdbcTemplate.update(sqlRemoveIngredient, cocktailId, ingredientId);
        } catch (EmptyResultDataAccessException emptyE) {
            throw new NotFoundException("Cocktail-Ingredient");
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public Integer amountOfCocktails(boolean showActive, boolean showInactive){
        try{
            return jdbcTemplate.queryForObject(sqlAmountOfCocktails, Integer.class, showActive ? true : null, showInactive ? false : null);
        }
        catch (DataAccessException rootException){
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }
}
