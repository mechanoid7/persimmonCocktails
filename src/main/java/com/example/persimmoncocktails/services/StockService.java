package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.StockDao;
import com.example.persimmoncocktails.dtos.stock.*;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.IncorrectRangeNumberFormat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StockService {
    StockDao stockDao;

    public void delete(Long ingredientId, Long personId) {
        stockDao.delete(ingredientId, personId);
    }

    public void addIngredient(RequestAddStockIngredientDto stockIngredients, Long personId) {
        stockDao.addIngredient(stockIngredients, personId);
    }

    public void update(Long personId, RequestStockUpdateDto requestStockUpdateDto) {
        stockDao.update(personId, requestStockUpdateDto);
    }

    public List<StockInfoDto> getStockIngredients(Long personId) {
        return stockDao.getStockIngredients(personId);
    }

    public List<RequestAddStockIngredientDto> searchIngredientByNameSubstring(String substring, Long pageNumber) {
        if (pageNumber<0) throw new IncorrectRangeNumberFormat("of page");
        return stockDao.searchIngredientByNameSubstring("%"+substring+"%", pageNumber);
    }

    public String buildSqlRequest(RequestStockIngredientSelectDto ingredientSelect) {
        String sqlSelect = "select i.ingredient_id, i.name, i.photo_id, i.ingredient_category_id, ic.name as category_name, s.amount, s.measure_type " +
                "from stock s " +
                "inner join ingredient i on i.ingredient_id = s.ingredient_id " +
                "inner join ingredient_category ic on i.ingredient_category_id = ic.ingredient_category_id where ";
        if(ingredientSelect.getIngredientCategoryId() != null) {
            //if exists
            sqlSelect += "i.ingredient_category_id="+ingredientSelect.getIngredientCategoryId()+"' and ";
        }
        if (ingredientSelect.getName() != null) { // search by name
            sqlSelect += "LOWER(d.name) LIKE '%" + ingredientSelect.getName().toLowerCase() + "%' AND ";
        }
        sqlSelect += "1=1 ORDER BY ";

        if (ingredientSelect.getSortBy() != null){ //sort
            sqlSelect += "i." + ingredientSelect.getSortBy().toLowerCase() + " ";
        } else {
            sqlSelect += "i.ingredient_id ";
        }
        if (ingredientSelect.getSortDirection() != null && // sort direction
                Boolean.FALSE.equals(ingredientSelect.getSortDirection())) { // if sort direction = false
            sqlSelect += "desc ";
        }
        sqlSelect += "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        System.out.println("SQL: "+sqlSelect);
        return sqlSelect;
    }

    public List<RequestAddStockIngredientDto> searchFilterSort(RequestStockIngredientSelectDto ingredientSelect, Long pageNumber) {

        if (ingredientSelect.getName() != null) {
            if (ingredientSelect.getName().length() < 2) throw new IncorrectNameFormat("Search request too short");
            //throw new IncorrectNameFormat();
        }
//        if (ingredientSelect.getSortBy() != null && !nameIsValid(ingredientSelect.getSortBy()))
//            throw new IncorrectNameFormat("Provided column name has incorrect format");
        if (pageNumber < 0) throw new IncorrectRangeNumberFormat("of page");
        return stockDao.searchFilterSort(buildSqlRequest(ingredientSelect), pageNumber);
    }
}
