package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.KitchenwareDao;
import com.example.persimmoncocktails.dtos.kitchenware.RequestKitchenwareDto;
import com.example.persimmoncocktails.dtos.kitchenware.ResponseKitchenwareDto;
import com.example.persimmoncocktails.exceptions.StateException;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.models.kitchenware.Kitchenware;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareCategory;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareWithCategory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KitchenwareService {
    KitchenwareDao kitchenwareDao;

    public KitchenwareWithCategory readKitchenwareId(Long kitchenwareId) {
        KitchenwareWithCategory kitchenware = kitchenwareDao.read(kitchenwareId);
        if(kitchenware == null) throw new NotFoundException("Kitchenware");
        return kitchenware;
    }

    public void updateName(Long kitchenwareId, String name){
        if(!kitchenwareDao.existsById(kitchenwareId)) throw new NotFoundException("Kitchenware");
        if(!AuthorizationService.nameIsValid(name)) throw new IncorrectNameFormat();
        Kitchenware kitchenware = kitchenwareDao.read(kitchenwareId).toKitchenware();
        kitchenware.setName(name);
        kitchenwareDao.update(kitchenware);
    }

    public void updateKitchenwareCategory(Long kitchenwareId, Long kitchenwareCategoryId){
        if(!kitchenwareDao.existsById(kitchenwareId)) throw new NotFoundException("Kitchenware");
        if(!kitchenwareDao.existsCategoryById(kitchenwareCategoryId)) throw new NotFoundException("KitchenwareCategory");
        Kitchenware kitchenware = kitchenwareDao.read(kitchenwareId).toKitchenware();
        kitchenware.setKitchenwareCategoryId(kitchenwareCategoryId);
        kitchenwareDao.update(kitchenware);
    }

    public void updatePhoto(Long kitchenwareId, Long photoId){
        if(!kitchenwareDao.existsById(kitchenwareId)) throw new NotFoundException("Kitchenware");
        // check photoId
        Kitchenware kitchenware = kitchenwareDao.read(kitchenwareId).toKitchenware();
        kitchenware.setPhotoId(photoId);
        kitchenwareDao.update(kitchenware);
    }

    public KitchenwareWithCategory createKitchenware(RequestKitchenwareDto requestKitchenwareDto){
        if(requestKitchenwareDto.getKitchenwareCategoryId() != null &&
                !kitchenwareDao.existsCategoryById(requestKitchenwareDto.getKitchenwareCategoryId()))
            throw new NotFoundException("KitchenwareCategory");
        Kitchenware kitchenware = new Kitchenware(null,
                requestKitchenwareDto.getName(),
                requestKitchenwareDto.getPhotoId(),
                requestKitchenwareDto.getKitchenwareCategoryId(),
                true
        );
        kitchenwareDao.create(kitchenware);
        return kitchenwareDao.readByName(requestKitchenwareDto.getName());
    }

    public List<KitchenwareWithCategory> readAllKitchenwares() {
        return kitchenwareDao.readAllKitchenwares();
    }

    public List<KitchenwareCategory> readAllKitchenwareCategories() {
        return kitchenwareDao.readAllKitchenwareCategories();
    }

    public void deactivate(Long kitchenwareId) {
        if(!kitchenwareDao.existsById(kitchenwareId)) throw new NotFoundException("Kitchenware");
        KitchenwareWithCategory kitchenware = kitchenwareDao.read(kitchenwareId);
        if(!kitchenware.isActive()) throw new StateException("This item is deactivated already");
        kitchenware.setActive(false);
        kitchenwareDao.update(kitchenware.toKitchenware());
    }

    public void activate(Long kitchenwareId) {
        if(!kitchenwareDao.existsById(kitchenwareId)) throw new NotFoundException("Kitchenware");
        KitchenwareWithCategory kitchenware = kitchenwareDao.read(kitchenwareId);
        if(!kitchenware.isActive()) throw new StateException("This item is activate already");
        kitchenware.setActive(true);
        kitchenwareDao.update(kitchenware.toKitchenware());
    }

    public List<ResponseKitchenwareDto> readAllActiveKitchenwares() {
        return kitchenwareDao.readAllKitchenwares()
                .stream()
                .filter(KitchenwareWithCategory::isActive)
                .map(ResponseKitchenwareDto::toDto)
                .collect(Collectors.toList());

    }

    public ResponseKitchenwareDto readActiveKitchenwareId(Long kitchenwareId) {
        KitchenwareWithCategory kitchenware = readKitchenwareId(kitchenwareId);
        if(!kitchenware.isActive()) throw new NotFoundException("Kitchenware");
        return ResponseKitchenwareDto.toDto(kitchenware);
    }
}
