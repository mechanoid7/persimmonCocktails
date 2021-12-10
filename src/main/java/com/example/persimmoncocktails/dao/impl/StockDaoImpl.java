package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.StockDao;
import com.example.persimmoncocktails.dtos.cocktail.BasicCocktailDto;
import com.example.persimmoncocktails.dtos.cocktail.FullCocktailDto;
import com.example.persimmoncocktails.dtos.stock.RequestStockSearchIngredientDto;
import com.example.persimmoncocktails.dtos.stock.StockInfoDto;
import com.example.persimmoncocktails.dtos.stock.RequestAddStockIngredientDto;
import com.example.persimmoncocktails.dtos.stock.RequestStockUpdateDto;
import com.example.persimmoncocktails.exceptions.UnknownException;

import com.example.persimmoncocktails.mappers.stock.StockFilterMapper;
import com.example.persimmoncocktails.mappers.stock.StockIngredientsMapper;
import com.example.persimmoncocktails.mappers.stock.StockMapper;
import com.example.persimmoncocktails.models.ingredient.IngredientWithCategory;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareWithCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.dao.DataAccessException;
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

    @Value("INSERT INTO stock (person_id, ingredient_id, amount, measure_type) VALUES (?, ?, ?, ?)")
    private String sqlInsertNewIngredient;

    @Value("${sqlReadStockByPersonId}")
    private String sqlReadStockByPersonId;

    @Value("UPDATE stock SET amount=? WHERE person_id = ? AND ingredient_id = ?;")
    private String sqlUpdateAmountOfIngredient;

    @Value("UPDATE stock SET amount=?, measure_type=? WHERE person_id = ? AND ingredient_id = ?;")
    private String sqlUpdateStock;

    @Value("${sqlIngredientDelete}")
    private String sqlIngredientDelete;

    @Value("Select s.ingredient_id, i.name from stock s Inner Join ingredient i on s.ingredient_id = i.ingredient_id where s.person_id = ? and lower(name) like ? order by ingredient_id offset ? rows fetch next ? rows only;")
    private String sqlGetListOfIngredientsBySubstring;

    @Value("15")
    private Long ingredientsPerPage;

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
    public void delete(Long ingredientId, Long personId) {
        jdbcTemplate.update(sqlIngredientDelete, ingredientId, personId);
    }

    @Override
    public void update(Long personId, RequestStockUpdateDto requestStockUpdateDto) {
        jdbcTemplate.update(sqlUpdateStock, requestStockUpdateDto.getAmount(),
                requestStockUpdateDto.getMeasureType(), personId, requestStockUpdateDto.getIngredientId());
    }

    @Override
    public void updateAmount(int amount, Long ingredientId) {
        jdbcTemplate.update(sqlUpdateAmountOfIngredient, amount, ingredientId);
    }

    @Override
    public List<StockInfoDto> getStockIngredients(Long personId) {
        return jdbcTemplate.query(sqlReadStockByPersonId, stockIngredientsMapper, personId);
    }

    @Override
    public List<RequestStockSearchIngredientDto> searchIngredientByNameSubstring(Long personId, String substring, Long pageNumber) {
        return jdbcTemplate.query(sqlGetListOfIngredientsBySubstring, stockMapper, personId,substring.toLowerCase(), pageNumber * ingredientsPerPage, ingredientsPerPage);
    }

    @Override
    public List<RequestAddStockIngredientDto> searchFilterSort(String sqlRequest, Long pageNumber) {
        return null;
        //return jdbcTemplate.query(sqlRequest, stockFilterMapper, pageNumber * ingredientsPerPage, ingredientsPerPage);
    }

    @Override
    public StockInfoDto getStockInfoDto(Long personId) {
        return new StockInfoDto();
    }

}
