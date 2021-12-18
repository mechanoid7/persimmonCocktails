package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.*;
import com.example.persimmoncocktails.dtos.cocktail.*;
import com.example.persimmoncocktails.exceptions.*;
import com.example.persimmoncocktails.models.cocktail.Cocktail;
import com.example.persimmoncocktails.models.cocktail.CocktailCategory;
import com.example.persimmoncocktails.models.ingredient.IngredientWithCategory;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareWithCategory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
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
@RequiredArgsConstructor
@PropertySources({
        @PropertySource("classpath:sql/cocktail_queries.properties"),
        @PropertySource("classpath:var/general.properties")
})
public class CocktailService {
    private final CocktailDao cocktailDao;
    private final PersonDao personDao;
    private final ImageDao imageDao;
    private final KitchenwareDao kitchenwareDao;
    private final IngredientDao ingredientDao;

    @Value("${number_of_cocktails_per_page}")
    Integer cocktailsPerPage;
    @Value("${sql_search_cocktails_base_query}")
    String searchCocktailsBaseSqlQuery;

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
        if(cocktail.getPhotoId() != null && !imageDao.isExistsById(cocktail.getPhotoId())) throw new NotFoundException("photoId");
        ingredientsAndKitchenwareAreValid(cocktail.getUniqueIngredientIds(), cocktail.getUniqueKitchenwareIds());
        BasicCocktailDto res = cocktailDao.create(cocktail);
        updateLabels(res.getDishId(), cocktail.getLabels());
        return readById(res.getDishId(), true);
    }

    private void ingredientsAndKitchenwareAreValid(List<Long> uniqueIngredientIds, List<Long> uniqueKitchenwareIds) {
        //TODO: optimize check queries
        if (uniqueKitchenwareIds != null) {
            for (Long kitchenwareId : uniqueKitchenwareIds) {
                boolean exists = kitchenwareDao.existsById(kitchenwareId);
                if (!exists) {
                    throw new NotFoundException("Kitchenware");
                }
            }
        }
        if (uniqueIngredientIds != null) {
            for (Long ingredientId : uniqueIngredientIds) {
                boolean exists = ingredientDao.existsById(ingredientId);
                if (!exists) {
                    throw new NotFoundException("Ingredient");
                }
            }
        }
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
        if(cocktail.getPhotoId() != null && !imageDao.isExistsById(cocktail.getPhotoId())) throw new NotFoundException("photoId");
        ingredientsAndKitchenwareAreValid(cocktail.getUniqueIngredientIds(), cocktail.getUniqueKitchenwareIds());
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
        return cocktailDao.searchFilterSort(buildSqlRequest(cocktailSelect, pageNumber));
    }

    public Integer amountOfResultPages(RequestCocktailSelectDto cocktailSelect){
        Integer resultsAmount;
        if(cocktailSelect.isClear()) {
            resultsAmount = cocktailDao.amountOfCocktails(cocktailSelect.getShowActive(), cocktailSelect.getShowInactive());
            return (int)Math.ceil(
                    (double)resultsAmount /
                            cocktailsPerPage);
        }
        List<Object> values = new ArrayList<>();
        StringBuilder wholeRequest = new StringBuilder("SELECT COUNT(*) FROM dish d WHERE ");
        wholeRequest.append(constructFilterQueryPart(cocktailSelect, values));
        wholeRequest.append(" 1=1");
        resultsAmount = cocktailDao.amountOfResults(new SqlSearchRequest(wholeRequest.toString(), values));
        return (int)Math.ceil((double)resultsAmount / cocktailsPerPage);
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

    public SqlSearchRequest buildSqlRequest(RequestCocktailSelectDto cocktailSelect, Long pageNumber) {
        List<Object> values = new ArrayList<>();
        StringBuilder sqlSelect = new StringBuilder(searchCocktailsBaseSqlQuery);
        sqlSelect.append(constructFilterQueryPart(cocktailSelect, values));
        sqlSelect.append("1=1 ORDER BY ");
        if (cocktailSelect.getSortBy() != null) { //sort
            if (!cocktailDao.columnExists(cocktailSelect.getSortBy().toLowerCase()))
                throw new NotFoundException("sort column");     // check column exists
            switch (cocktailSelect.getSortBy().toLowerCase()){
                case "name": sqlSelect.append("d.name "); break;
                case "receipt": sqlSelect.append("d.receipt "); break;
                case "likes": sqlSelect.append("d.likes "); break;
                case "description": sqlSelect.append("d.description "); break;
            }
//            values.add(cocktailSelect.getSortBy().toLowerCase());
        } else {
            sqlSelect.append("d.dish_id ");
        }
        if (cocktailSelect.getSortDirection() != null && // sort direction
                Boolean.FALSE.equals(cocktailSelect.getSortDirection())) { // if sort direction = false
            sqlSelect.append("desc ");
        }
        sqlSelect.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        values.add(pageNumber*cocktailsPerPage);
        values.add(cocktailsPerPage);
//        System.out.println("SQL: " + sqlSelect);
        return new SqlSearchRequest(sqlSelect.toString(), values);
    }

    private String constructFilterQueryPart(RequestCocktailSelectDto cocktailSelect, List<Object> values) {
        StringBuilder sqlSelect = new StringBuilder();
        if (!cocktailSelect.getShowActive()) sqlSelect.append("d.is_active <> true AND ");
        if (!cocktailSelect.getShowInactive()) sqlSelect.append("d.is_active <> false AND ");
        if (cocktailSelect.getDishCategoryId() != null) { // filter
            values.add(cocktailSelect.getDishCategoryId());
            sqlSelect.append("d.dish_category_id= ? AND ");
        }
        if (cocktailSelect.getName() != null) { // search by name
            values.add("%"+cocktailSelect.getName().toLowerCase()+"%");
            sqlSelect.append("LOWER(d.name) LIKE ? AND ");
        }
        if (cocktailSelect.getIngredients() != null && !cocktailSelect.getIngredients().isEmpty()) { // filter by ingredients
            sqlSelect.append("(SELECT 0 = (SELECT COUNT(*) FROM (")
                    .append(unionOfValues(cocktailSelect.getIngredients(), values))
                    .append("\nEXCEPT\nSELECT ingridient_id FROM ingridient_dish WHERE dish_id = d.dish_id) AS a)) AND ");
        }
        return sqlSelect.toString();
    }

    private String unionOfValues(List<Long> ingredients, List<Object> values) {
        StringBuilder sb = new StringBuilder("(");
        Iterator<Long> iterator = ingredients.listIterator();
        while (iterator.hasNext()) {
            long ingredientId = iterator.next();
            sb.append("VALUES(?)");
            values.add(ingredientId);
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
