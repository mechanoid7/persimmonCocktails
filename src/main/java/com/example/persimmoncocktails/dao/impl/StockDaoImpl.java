package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.StockDao;
import com.example.persimmoncocktails.dtos.stock.StockIngredientsDto;
import com.example.persimmoncocktails.exceptions.UnknownException;

import com.example.persimmoncocktails.mappers.StockMapper;
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
    private StockMapper stockMapper;

    @Value("INSERT INTO stock (person_id, ingredient_id, amount, measure_type) VALUES (?, ?, ?, ?)")
    private String sqlInsertNewIngredient;

//    @Value("SELECT person_id, ingredient_id, amount, measure_type FROM stock WHERE stock_id = ?")
//    private String sqlReadStockById;

    @Value("SELECT person_id, ingredient_id, amount, measure_type FROM stock WHERE person_id = ?")
    private String sqlReadStockByPersonId;

    @Value("UPDATE stock SET amount=? WHERE person_id = ? AND ingredient_id = ?;")
    private String sqlUpdateIngredientInStock;

    @Value("DELETE FROM stock WHERE person_id = ? and ingredient_id = ?")
    private String sqlDeleteIngredientFromStock;

    @Value("Select ingredient_id, name where lower(name) like ? order by ingredient_id offset ? rows fetch next ? rows only;")
    private String sqlGetListOfIngredientsBySubstring;

    @Value("15")
    private  Long ingredientsPerPage;

    @Override
    public void add(StockIngredientsDto stockIngredientsDto) {
        try {
            jdbcTemplate.update(sqlInsertNewIngredient, stockIngredientsDto.getName(), stockIngredientsDto.getMeasureType(), stockIngredientsDto.getAmount(), stockIngredientsDto.getPhotoId());
        } catch (DataAccessException rootException) {
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void delete(Long ingredientId) {
        jdbcTemplate.update(sqlDeleteIngredientFromStock, stockMapper, ingredientId);
    }

    @Override
    public void update(int amount, Long ingredientId, Long photoId) {
        jdbcTemplate.update(sqlUpdateIngredientInStock, stockMapper, amount, ingredientId, photoId);
    }

    @Override
    public List<StockIngredientsDto> getStockIngredients(Long personId) {
        return jdbcTemplate.query(sqlReadStockByPersonId, stockMapper, personId);

    }

    @Override
    public List<StockIngredientsDto> searchIngredientByNameSubstring(String substring, Long pageNumber) {
        return jdbcTemplate.query(sqlGetListOfIngredientsBySubstring, stockMapper, substring.toLowerCase(), pageNumber * ingredientsPerPage, ingredientsPerPage);
    }

    @Override
    public List<StockIngredientsDto> searchFilterSort(String sqlRequest, Long pageNumber) {
        return jdbcTemplate.query(sqlRequest, stockMapper, pageNumber * ingredientsPerPage, ingredientsPerPage);
    }
}
