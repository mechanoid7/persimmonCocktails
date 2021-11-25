package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.CocktailDao;
import com.example.persimmoncocktails.dao.FriendsDao;
import com.example.persimmoncocktails.dtos.cocktail.RequestCreateCocktail;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.IncorrectRangeNumberFormat;
import com.example.persimmoncocktails.exceptions.WrongCredentialsException;
import com.example.persimmoncocktails.models.Cocktail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CocktailService {
    CocktailDao cocktailDao;

    public Cocktail readById(Long dishId) {
        return cocktailDao.readById(dishId);
    }

    public void create(RequestCreateCocktail cocktail) {
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

    public List<Cocktail> searchFilterSort(String substring, Long pageNumber) {
        if (!AuthorizationService.nameIsValid(substring)) throw new IncorrectNameFormat();
        if (pageNumber < 0) throw new IncorrectRangeNumberFormat("of page");

        return null;
    }

    public void changeStatus(Long dishId) {
        if (cocktailDao.cocktailIsActive(dishId))
            cocktailDao.deactivateCocktailById(dishId);
        else cocktailDao.activateCocktailById(dishId); // if disabled
    }

    public Boolean isActive(Long dishId) {
        return cocktailDao.cocktailIsActive(dishId);
    }

}
