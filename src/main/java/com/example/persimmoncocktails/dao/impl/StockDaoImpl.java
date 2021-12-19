package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.StockDao;
import com.example.persimmoncocktails.dtos.cocktail.BasicCocktailDto;
import com.example.persimmoncocktails.dtos.cocktail.FullCocktailDto;
import com.example.persimmoncocktails.dtos.stock.*;
import com.example.persimmoncocktails.exceptions.UnknownException;

import com.example.persimmoncocktails.mappers.stock.StockFilterMapper;
import com.example.persimmoncocktails.mappers.stock.StockIngredientMapper;
import com.example.persimmoncocktails.mappers.stock.StockIngredientsMapper;
import com.example.persimmoncocktails.mappers.stock.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PropertySources({
        @PropertySource("classpath:sql/stock_queries.properties"),
        @PropertySource("classpath:var/general.properties")
})

@RequiredArgsConstructor
public class StockDaoImpl implements StockDao {

    private final JdbcTemplate jdbcTemplate;
    private final StockMapper stockMapper = new StockMapper();
    private final StockIngredientsMapper stockIngredientsMapper = new StockIngredientsMapper();
    private final StockFilterMapper stockFilterMapper = new StockFilterMapper();
    private final StockIngredientMapper stockIngredientMapper = new StockIngredientMapper();

    @Value("${sql_add_ingredient}")
    private String sqlInsertNewIngredient;

    @Value("${sql_add_ingredient_id}")
    private String sqlInsertNewIngredientId;

    @Value("${sqlReadStockByPersonId}")
    private String sqlReadStockByPersonId;

    @Value("${sql_stock_update}")
    private String sqlUpdateStock;

    @Value("${sqlIngredientDelete}")
    private String sqlIngredientDelete;

    @Value("${sql_get_stock_ingredients_by_substring}")
    private String sqlGetListOfIngredientsBySubstring;

    @Value("${sqlReadStockIngredient}")
    private String sqlGetStockIngredient;

    @Value("15")
    private Long ingredientsPerPage;


    @Override
    public StockInfoDto getStockIngredient(Long personId, Long ingredientId) {
        try {
            return jdbcTemplate.queryForObject(sqlGetStockIngredient, stockIngredientMapper, personId, ingredientId);
        } catch (EmptyResultDataAccessException emptyE) {
            return null;
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }

    }

    @Override
    public void addIngredient(RequestAddStockIngredientDto requestAddStockIngredientDto, Long personId) {
        try {
            jdbcTemplate.update(sqlInsertNewIngredient, personId, requestAddStockIngredientDto.getIngredientId(), requestAddStockIngredientDto.getAmount(), requestAddStockIngredientDto.getMeasureType());
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void addIngredientId(RequestStockIngredientIdDto requestStockIngredientIdDto, Long personId) {
        try {
            jdbcTemplate.update(sqlInsertNewIngredientId, personId, requestStockIngredientIdDto.getIngredientId());
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void delete(Long ingredientId, Long personId) {
        jdbcTemplate.update(sqlIngredientDelete, ingredientId, personId);
    }

    @Override
    public void update(Long personId, RequestStockUpdateDto requestStockUpdateDto) {
        jdbcTemplate.update(sqlUpdateStock, requestStockUpdateDto.getAmount(),
                requestStockUpdateDto.getMeasureType(), personId, requestStockUpdateDto.getIngredientId());
    }

    @Override
    public List<StockInfoDto> getStockIngredients(Long personId, Long pageNumber) {
        return jdbcTemplate.query(sqlReadStockByPersonId, stockIngredientsMapper, personId, pageNumber * ingredientsPerPage, ingredientsPerPage);
    }

    @Override
    public List<RequestStockSearchIngredientDto> searchIngredientByNameSubstring(Long personId, String substring, Long pageNumber) {
        return jdbcTemplate.query(sqlGetListOfIngredientsBySubstring, stockMapper, personId, substring.toLowerCase(), pageNumber * ingredientsPerPage, ingredientsPerPage);
    }

    @Override
    public List<StockInfoDto> searchFilterSort(String sqlRequest, Long pageNumber) {
        return jdbcTemplate.query(sqlRequest, stockFilterMapper, pageNumber * ingredientsPerPage, ingredientsPerPage);
    }

    @Override
    public StockInfoDto getStockInfoDto(Long personId) {
        return new StockInfoDto();
    }
}
