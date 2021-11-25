package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.CocktailDao;
import com.example.persimmoncocktails.dao.FriendsDao;
import com.example.persimmoncocktails.dtos.cocktail.RequestCocktailSelectDto;
import com.example.persimmoncocktails.dtos.cocktail.RequestCreateCocktail;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.IncorrectRangeNumberFormat;
import com.example.persimmoncocktails.exceptions.WrongCredentialsException;
import com.example.persimmoncocktails.models.Cocktail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CocktailService {
    CocktailDao cocktailDao;

    public Cocktail readById(Long dishId) {
        return cocktailDao.readById(dishId);
    }

    public void create(RequestCreateCocktail cocktail) {
        if (!nameIsValid(cocktail.getName())) throw new IncorrectNameFormat();
        cocktailDao.create(cocktail);
    }

    public void addLikes(Long dishId, Long likeNumber) {
        if (likeNumber < 1) throw new IncorrectRangeNumberFormat("of likes");
        cocktailDao.addLikes(dishId, likeNumber);
    }

    public void addLike(Long dishId) {
        cocktailDao.addLikes(dishId, 1L);
    }

    public void deleteById(Long dishId) {
        cocktailDao.deleteById(dishId);
    }

    public void update(Cocktail cocktail) {
        cocktailDao.update(cocktail);
    }

    public List<Cocktail> searchFilterSort(RequestCocktailSelectDto cocktailSelect, Long pageNumber) {
        if (cocktailSelect.isClear())
            return cocktailDao.getRawListOfCocktails(pageNumber); // list of cocktails without search/filter/sort
        if (cocktailSelect.getName() != null && !nameIsValid(cocktailSelect.getName()))
            throw new IncorrectNameFormat();
        if (pageNumber < 0) throw new IncorrectRangeNumberFormat("of page");
        return cocktailDao.searchFilterSort(buildSqlRequest(cocktailSelect), pageNumber);
    }

    public void changeStatus(Long dishId) {
        if (cocktailDao.cocktailIsActive(dishId))
            cocktailDao.deactivateCocktailById(dishId);
        else cocktailDao.activateCocktailById(dishId); // if disabled
    }

    public Boolean isActive(Long dishId) {
        return cocktailDao.cocktailIsActive(dishId);
    }

    public static boolean nameIsValid(String name) {
        String regex = "^[a-zA-Z0-9 -]{2,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    public String buildSqlRequest(RequestCocktailSelectDto cocktailSelect) {
        String sqlSelect = "SELECT d.dish_id, d.name, d.description, d.dish_type, dc.name, d.label, d.receipt, d.likes, d.is_active " +
                "FROM dish d INNER JOIN dish_category dc ON d.dish_category_id = dc.dish_category_id " +
                "WHERE ";
        if (cocktailSelect.getDishType() != null && // filter
                nameIsValid(cocktailSelect.getDishType())) {
            sqlSelect += "d.dish_type=" + cocktailSelect.getDishType() + " AND ";
        }
        if (cocktailSelect.getDishCategoryId() != null) { // filter
            sqlSelect += "d.dish_category_id=" + cocktailSelect.getDishCategoryId() + " AND ";
        }
        if (cocktailSelect.getName() != null &&  // search by name
                nameIsValid(cocktailSelect.getName())) {
            sqlSelect += "LOWER(name) LIKE %" + cocktailSelect.getName().toLowerCase() + "% AND ";
        }
        sqlSelect += "1=1 ORDER BY ";
        if (cocktailSelect.getSortBy() != null && //sort
                nameIsValid(cocktailSelect.getSortBy()) && // check name is correct
                cocktailDao.columnExists(cocktailSelect.getSortBy())) { // check column exists
            sqlSelect += "d." + cocktailSelect.getSortBy() + " ";
        } else {
            sqlSelect += "d.dish_id ";
        }
        if (cocktailSelect.getSortDirection() != null && // sort direction
                Boolean.FALSE.equals(cocktailSelect.getSortDirection())) { // if sort direction = false
            sqlSelect += "desc ";
        }
        sqlSelect += "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return sqlSelect;
    }

}
