package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.StockDao;
import com.example.persimmoncocktails.dtos.stock.StockIngredientsDto;
import com.example.persimmoncocktails.exceptions.UnknownException;

import com.example.persimmoncocktails.mappers.StockMapper;
import com.example.persimmoncocktails.models.Stock.Ingredient;
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

    @Value("INSERT INTO stock (stock_id, ingredient_id, amount, measure_type) VALUES (?, ?, ?, ?)")
    private String sqlInsertNewIngredient;

    @Value("SELECT person_id, stock_id, ingredient_id, amount, measure_type FROM stock WHERE stock_id = ?")
    private String sqlReadStockById;

    @Value("SELECT person_id, stock_id, ingredient_id, amount, measure_type FROM stock WHERE person_id = ?")
    private String sqlReadStockByPersonId;

    @Value("UPDATE stock SET amount=? WHERE stock_id = ? AND ingredient_id = ?;")
    private String sqlUpdateIngredientInStock;

    @Value("DELETE FROM stock WHERE stock_id = ? ingredient_id = ?")
    private String sqlDeleteIngredientFromStock;



    @Override
    public void add(StockIngredientsDto stockIngredientsDto) {
        try {
            jdbcTemplate.update(sqlInsertNewIngredient, stockIngredientsDto.getName(), stockIngredientsDto.getMeasureType(), stockIngredientsDto.getAmount());
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
    public void update(int amount, Long ingredientId) {
        jdbcTemplate.update(sqlUpdateIngredientInStock, stockMapper, amount, ingredientId);
    }

    @Override
    public List<StockIngredientsDto> getStockIngredients(Long stockId) {
        return jdbcTemplate.query(sqlReadStockById, stockMapper, stockId);

    }
}
