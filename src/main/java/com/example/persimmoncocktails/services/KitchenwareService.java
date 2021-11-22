package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.KitchenwareDao;
import com.example.persimmoncocktails.dtos.kitchenware.KitchenwareResponseDto;
import com.example.persimmoncocktails.dtos.kitchenware.RequestKitchenwareDto;
import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.models.Kitchenware;
import com.example.persimmoncocktails.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KitchenwareService {
    KitchenwareDao kitchenwareDao;

    public void delete(Long kitchenwareId){
        if(!kitchenwareDao.existsById(kitchenwareId)) throw new NotFoundException("Kitchenware");
        kitchenwareDao.delete(kitchenwareId);
    }

    public KitchenwareResponseDto readKitchenwareId(Long kitchenwareId) {
        Kitchenware kitchenware = kitchenwareDao.read(kitchenwareId);
        if(kitchenware == null) throw new NotFoundException("Kitchenware");
        return KitchenwareResponseDto.toDto(kitchenware);
    }

    public void updateName(Long kitchenwareId, String name){
        if(!kitchenwareDao.existsById(kitchenwareId)) throw new NotFoundException("Kitchenware");
        if(!AuthorizationService.nameIsValid(name)) throw new IncorrectNameFormat();
        Kitchenware kitchenware = kitchenwareDao.read(kitchenwareId);
        kitchenware.setName(name);
        kitchenwareDao.update(kitchenware);
    }

    public void updateKitchenwareCategory(Long kitchenwareId, Long kitchenwareCategoryId){
        if(!kitchenwareDao.existsById(kitchenwareId)) throw new NotFoundException("Kitchenware");
        // check kitchenwareCategoryId
        Kitchenware kitchenware = kitchenwareDao.read(kitchenwareId);
        kitchenware.setKitchenwareCategoryId(kitchenwareCategoryId);
        kitchenwareDao.update(kitchenware);
    }

    public void updatePhoto(Long kitchenwareId, Long photoId){
        if(!kitchenwareDao.existsById(kitchenwareId)) throw new NotFoundException("Kitchenware");
        // check photoId
        Kitchenware kitchenware = kitchenwareDao.read(kitchenwareId);
        kitchenware.setPhotoId(photoId);
        kitchenwareDao.update(kitchenware);
    }

    public KitchenwareResponseDto createKitchenware(RequestKitchenwareDto requestKitchenwareDto){
        Kitchenware kitchenware = new Kitchenware(null,
                requestKitchenwareDto.getName(),
                requestKitchenwareDto.getKitchenwareCategoryId(),
                requestKitchenwareDto.getPhotoId());
        kitchenwareDao.create(kitchenware);
        return KitchenwareResponseDto.toDto(kitchenwareDao.readByName(requestKitchenwareDto.getName()));
    }
}
