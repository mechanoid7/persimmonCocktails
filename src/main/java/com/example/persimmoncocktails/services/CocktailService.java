package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.CocktailDao;
import com.example.persimmoncocktails.dtos.cocktail.CocktailResponseDto;
import com.example.persimmoncocktails.dtos.cocktail.RequestCocktailSelectDto;
import com.example.persimmoncocktails.dtos.cocktail.RequestCocktailUpdate;
import com.example.persimmoncocktails.dtos.cocktail.RequestCreateCocktail;
import com.example.persimmoncocktails.exceptions.DuplicateException;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.IncorrectRangeNumberFormat;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CocktailService {
    CocktailDao cocktailDao;

    public CocktailResponseDto readById(Long dishId) {
        var cocktail = cocktailDao.readById(dishId);
        if(cocktail == null) throw new NotFoundException("Cocktail");
        return cocktail;
    }

    public void create(RequestCreateCocktail cocktail) {
        if (!nameIsValid(cocktail.getName())) throw new IncorrectNameFormat();
        cocktailDao.create(cocktail);
    }

    public void addLike(Long dishId, Long personId) {
        if (cocktailDao.likeExists(personId, dishId)) throw new DuplicateException("like this cocktail");
        cocktailDao.addLikeTable(personId, dishId);
        cocktailDao.addLikeCount(dishId);
    }

    public void deleteById(Long dishId) {
        if(!cocktailDao.existsById(dishId)) throw new NotFoundException("Cocktail");
        cocktailDao.deleteById(dishId);
    }

    public void update(RequestCocktailUpdate cocktail) {
        cocktailDao.update(cocktail);
    }

    public List<CocktailResponseDto> searchFilterSort(RequestCocktailSelectDto cocktailSelect, Long pageNumber) {
        if (cocktailSelect.isClear())
            return cocktailDao.getRawListOfCocktails(pageNumber); // list of cocktails without search/filter/sort
        if (cocktailSelect.getName() != null && !nameIsValid(cocktailSelect.getName())) {
            if (cocktailSelect.getName().length() < 2) throw new IncorrectNameFormat("Search request too short");
            throw new IncorrectNameFormat();
        }
        if (cocktailSelect.getSortBy() != null && !nameIsValid(cocktailSelect.getSortBy()))
            throw new IncorrectNameFormat("Provided column name has incorrect format");
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

    public static boolean labelIsValid(String label) {
        String regex = "^[a-zA-Z0-9-]{3,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(label);
        return matcher.find();
    }

    public String buildSqlRequest(RequestCocktailSelectDto cocktailSelect) {
        String sqlSelect = "SELECT d.dish_id, d.name, d.description, d.dish_type, dc.name dish_name, d.dish_category_id category_id, d.label, d.receipt, d.likes, d.is_active " +
                "FROM dish d LEFT JOIN dish_category dc ON d.dish_category_id = dc.dish_category_id " +
                "WHERE ";
        if (cocktailSelect.getDishType() != null && // filter
                nameIsValid(cocktailSelect.getDishType())) {
            if (!cocktailDao.dishTypeExists(cocktailSelect.getDishType())) throw new NotFoundException("dish type");
            sqlSelect += "d.dish_type='" + cocktailSelect.getDishType().toLowerCase() + "' AND ";
        }
        if (cocktailSelect.getDishCategoryId() != null) { // filter
            sqlSelect += "d.dish_category_id=" + cocktailSelect.getDishCategoryId() + " AND ";
        }
        if (cocktailSelect.getName() != null) { // search by name
            sqlSelect += "LOWER(d.name) LIKE '%" + cocktailSelect.getName().toLowerCase() + "%' AND ";
        }
        sqlSelect += "1=1 ORDER BY ";
        if (cocktailSelect.getSortBy() != null){ //sort
            if (!cocktailDao.columnExists(cocktailSelect.getSortBy().toLowerCase())) throw new NotFoundException("sort column");     // check column exists
            sqlSelect += "d." + cocktailSelect.getSortBy().toLowerCase() + " ";
        } else {
            sqlSelect += "d.dish_id ";
        }
        if (cocktailSelect.getSortDirection() != null && // sort direction
                Boolean.FALSE.equals(cocktailSelect.getSortDirection())) { // if sort direction = false
            sqlSelect += "desc ";
        }
        sqlSelect += "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        System.out.println("SQL: "+sqlSelect);
        return sqlSelect;
    }

    private List<String> labelsFromString(String str){
        if(str == null || str.equals("")) return new ArrayList<>();
        return new ArrayList<>(List.of(str.split(";")));
    }

    public void addLabel(Long dishId, String label) {
        if (!labelIsValid(label)) throw new IncorrectNameFormat("Label name has incorrect format.");
        String labels = cocktailDao.getLabels(dishId);
        cocktailDao.updateLabels(dishId, (labelsFromString(labels).isEmpty() ? "" : (labels+";"))+label);
    }

    public List<String> getLabels(Long dishId) {
        return labelsFromString(cocktailDao.getLabels(dishId));
    }

    public void updateLabels(Long dishId, List<String> label) {
        cocktailDao.updateLabels(dishId, String.join(";", label));
    }

    public void deleteLabel(Long dishId, String label) {
        List<String> labels = getLabels(dishId);
        if (!labels.contains(label)) throw new NotFoundException("label '"+label+"'");
        labels.remove(label);
        cocktailDao.updateLabels(dishId, String.join(";", labels));
    }

    public void clearLabelsLabels(Long dishId) {
        cocktailDao.updateLabels(dishId,"");
    }

    public Long getLikes(Long dishId){
        return cocktailDao.getLikes(dishId);
    }
}
