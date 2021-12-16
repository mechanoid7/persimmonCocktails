package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.CocktailDao;
import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.cocktail.*;
import com.example.persimmoncocktails.exceptions.DuplicateException;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.IncorrectRangeNumberFormat;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.models.cocktail.Cocktail;
import com.example.persimmoncocktails.models.cocktail.CocktailCategory;
import com.example.persimmoncocktails.models.ingredient.IngredientWithCategory;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareWithCategory;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CocktailService {
    CocktailDao cocktailDao;
    PersonDao personDao;

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

    public static List<String> labelsFromString(String str) {
        if (str == null || str.equals("")) return new ArrayList<>();
        return new ArrayList<>(List.of(str.split(";")));
    }

    public FullCocktailDto readById(Long dishId, boolean isAuthorizedToSeeNotActive) {
        FullCocktailDto cocktail = cocktailDao.getFullCocktailInfo(dishId);
        if (cocktail == null || (!cocktail.getIsActive() && !isAuthorizedToSeeNotActive)) throw new NotFoundException("Cocktail");
        if (!isAuthorizedToSeeNotActive) cocktail = hideNotActive(cocktail);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getAuthorities().stream().noneMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))){
            cocktail.setHasLike(cocktailDao.likeExists((Long) authentication.getDetails(), dishId));
        }
        return cocktail;
    }

    private FullCocktailDto hideNotActive(FullCocktailDto cocktail) {
        if (!cocktail.getIsActive()) return null;
        cocktail.setIngredientList(cocktail.getIngredientList()
                .stream()
                .filter(IngredientWithCategory::isActive)
                .collect(Collectors.toList()));
        cocktail.setKitchenwareList(cocktail.getKitchenwareList()
                .stream()
                .filter(KitchenwareWithCategory::isActive)
                .collect(Collectors.toList()));
        return cocktail;
    }

    public FullCocktailDto create(RequestCreateCocktail cocktail) {
        if (!nameIsValid(cocktail.getName())) throw new IncorrectNameFormat();
        BasicCocktailDto res = cocktailDao.create(cocktail);
        updateLabels(res.getDishId(), cocktail.getLabels());
        return readById(res.getDishId(), true);
    }

    public void addLike(Long dishId, Long personId) {
        if (cocktailDao.likeExists(personId, dishId)) throw new DuplicateException("like this cocktail");
        cocktailDao.addLikeTable(personId, dishId);
        cocktailDao.addLikeCount(dishId);
    }

    public void deleteById(Long dishId) {
        if (!cocktailDao.existsById(dishId)) throw new NotFoundException("Cocktail");
        cocktailDao.deleteById(dishId);
    }

    public void update(RequestCocktailUpdate cocktail) {
        cocktailDao.update(cocktail);
        FullCocktailDto updated = readById(cocktail.getDishId(), true);
        updateIngredients(updated, cocktail.getIngredientList());
        updateKitchenwares(updated, cocktail.getKitchenwareIds());
        updateLabels(cocktail.getDishId(), cocktail.getLabels());
    }

    private void updateKitchenwares(FullCocktailDto updated, List<Long> kitchenwareList) {
        for(KitchenwareWithCategory kitchenware : updated.getKitchenwareList()){
            if(!kitchenwareList.contains(kitchenware.getKitchenwareId())){
                removeKitchenware(new RequestKitchenwareCocktailDto(kitchenware.getKitchenwareId(), updated.getDishId()));
            }
        }
        for(Long kitchenwareId : kitchenwareList){
            if(updated.getKitchenwareList().stream().noneMatch(i -> i.getKitchenwareId().equals(kitchenwareId))){
                addKitchenware(new RequestKitchenwareCocktailDto(kitchenwareId, updated.getDishId()));
            }
        }
    }

    @Transactional
    void updateIngredients(FullCocktailDto updated, List<Long> ingredientList) {
        for(IngredientWithCategory ingredient : updated.getIngredientList()){
            if(!ingredientList.contains(ingredient.getIngredientId())){
                removeIngredient(new RequestIngredientCocktailDto(ingredient.getIngredientId(), updated.getDishId()));
            }
        }
        for(Long ingredientId : ingredientList){
            if(updated.getIngredientList().stream().noneMatch(i -> i.getIngredientId().equals(ingredientId))){
                addIngredient(new RequestIngredientCocktailDto(ingredientId, updated.getDishId()));
            }
        }
    }

    public List<BasicCocktailDto> searchFilterSort(RequestCocktailSelectDto cocktailSelect, Long pageNumber) {
        if (cocktailSelect.isClear())
            return getRawListOfCocktails(pageNumber, cocktailSelect.getShowActive(), cocktailSelect.getShowInactive());
        // list of cocktails without search/filter/sort
        if (cocktailSelect.getName() != null && !nameIsValid(cocktailSelect.getName())) {
            if (cocktailSelect.getName().length() < 2) throw new IncorrectNameFormat("Search request too short");
            throw new IncorrectNameFormat();
        }
        if (cocktailSelect.getSortBy() != null && !nameIsValid(cocktailSelect.getSortBy()))
            throw new IncorrectNameFormat("Provided column name has incorrect format");
        if (pageNumber < 0) throw new IncorrectRangeNumberFormat("of page");
        return cocktailDao.searchFilterSort(buildSqlRequest(cocktailSelect), pageNumber);
    }

    private List<BasicCocktailDto> getRawListOfCocktails(Long pageNumber, Boolean showActive, Boolean showInactive) {
        List<BasicCocktailDto> cocktails = cocktailDao.getRawListOfCocktails(pageNumber);
        return cocktails
                .stream()
                .filter(c -> c.getIsActive() ? showActive : showInactive)
                .collect(Collectors.toList());
    }

    public void changeStatus(Long dishId) {
        if (cocktailDao.cocktailIsActive(dishId))
            cocktailDao.deactivateCocktailById(dishId);
        else cocktailDao.activateCocktailById(dishId); // if disabled
    }

    public Boolean isActive(Long dishId) {
        return cocktailDao.cocktailIsActive(dishId);
    }

    public String buildSqlRequest(RequestCocktailSelectDto cocktailSelect) {
        String sqlSelect = "SELECT d.dish_id, im.photo_id, im.url_full, im.url_middle, im.url_thumb, im.url_delete," +
                "d.name, d.description, d.dish_type, dc.name dish_category_name, d.dish_category_id category_id, " +
                "d.label, d.receipt, d.likes, d.is_active FROM dish d " +
                "LEFT JOIN dish_category dc ON d.dish_category_id = dc.dish_category_id " +
                "LEFT JOIN image im ON d.image_id = im.photo_id " +
                "WHERE ";
        if (!cocktailSelect.getShowActive()) sqlSelect += "d.is_active <> true AND ";
        if (!cocktailSelect.getShowInactive()) sqlSelect += "d.is_active <> false AND ";
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
        if (cocktailSelect.getIngredients() != null && !cocktailSelect.getIngredients().isEmpty()) { // filter by ingredients
            sqlSelect += "(SELECT 0 = (SELECT COUNT(*) FROM (" + unionOfValues(cocktailSelect.getIngredients()) + "\n" +
                    "EXCEPT\n" +
                    "SELECT ingridient_id FROM ingridient_dish WHERE dish_id = d.dish_id) AS a)) AND ";
        }
        sqlSelect += "1=1 ORDER BY ";
        if (cocktailSelect.getSortBy() != null) { //sort
            if (!cocktailDao.columnExists(cocktailSelect.getSortBy().toLowerCase()))
                throw new NotFoundException("sort column");     // check column exists
            sqlSelect += "d." + cocktailSelect.getSortBy().toLowerCase() + " ";
        } else {
            sqlSelect += "d.dish_id ";
        }
        if (cocktailSelect.getSortDirection() != null && // sort direction
                Boolean.FALSE.equals(cocktailSelect.getSortDirection())) { // if sort direction = false
            sqlSelect += "desc ";
        }
        sqlSelect += "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        System.out.println("SQL: " + sqlSelect);
        return sqlSelect;
    }

    private String unionOfValues(List<Long> ingredients) {
        StringBuilder sb = new StringBuilder("(");
        Iterator<Long> iterator = ingredients.listIterator();
        while (iterator.hasNext()) {
            long ingredientId = iterator.next();
            sb.append("VALUES(").append(ingredientId).append(")");
            if (iterator.hasNext()) sb.append(" UNION ");
        }
        sb.append(")");
        return sb.toString();
    }

    public void addLabel(Long dishId, String label) {
        if (!labelIsValid(label)) throw new IncorrectNameFormat("Label name has incorrect format.");
        String labels = cocktailDao.getLabels(dishId);
        cocktailDao.updateLabels(dishId, (labelsFromString(labels).isEmpty() ? "" : (labels + ";")) + label);
    }

    public List<String> getLabels(Long dishId) {
        return labelsFromString(cocktailDao.getLabels(dishId));
    }

    public void updateLabels(Long dishId, List<String> label) {
        cocktailDao.updateLabels(dishId, String.join(";", label));
    }

    public void deleteLabel(Long dishId, String label) {
        List<String> labels = getLabels(dishId);
        if (!labels.contains(label)) throw new NotFoundException("label '" + label + "'");
        labels.remove(label);
        cocktailDao.updateLabels(dishId, String.join(";", labels));
    }

    public void clearLabelsLabels(Long dishId) {
        cocktailDao.updateLabels(dishId, "");
    }

    public Long getLikes(Long dishId) {
        return cocktailDao.getLikes(dishId);
    }

    public List<CocktailCategory> getCocktailCategories() {
        return cocktailDao.getCocktailCategories();
    }

    public void addIngredient(RequestIngredientCocktailDto request) {
        if (cocktailDao.hasIngredient(request.getCocktailId(), request.getIngredientId()))
            throw new DuplicateException("Ingredient in Cocktail");
        cocktailDao.addIngredient(request.getCocktailId(), request.getIngredientId());
    }

    public void removeIngredient(RequestIngredientCocktailDto request) {
        if (!cocktailDao.hasIngredient(request.getCocktailId(), request.getIngredientId()))
            throw new NotFoundException("Ingredient in Cocktail");
        cocktailDao.removeIngredient(request.getCocktailId(), request.getIngredientId());
    }

    public void addKitchenware(RequestKitchenwareCocktailDto request) {
        if (cocktailDao.hasKitchenware(request.getCocktailId(), request.getKitchenwareId()))
            throw new DuplicateException("Kitchenware in Cocktail");
        cocktailDao.addKitchenware(request.getCocktailId(), request.getKitchenwareId());
    }

    public void removeKitchenware(RequestKitchenwareCocktailDto request) {
        if (!cocktailDao.hasKitchenware(request.getCocktailId(), request.getKitchenwareId()))
            throw new NotFoundException("Kitchenware in Cocktail");
        cocktailDao.removeKitchenware(request.getCocktailId(), request.getKitchenwareId());
    }

    public void updateImage(RequestChangeImageDto requestChangeImage) {
        if (!cocktailDao.existsById(requestChangeImage.getCocktailId())) throw new NotFoundException("Cocktail");
        cocktailDao.updateImage(requestChangeImage.getCocktailId(), requestChangeImage.getImageId());
    }
}
