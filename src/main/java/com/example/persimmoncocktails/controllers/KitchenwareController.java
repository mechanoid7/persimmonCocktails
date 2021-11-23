package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.kitchenware.RequestKitchenwareDto;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareCategory;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareWithCategory;
import com.example.persimmoncocktails.services.KitchenwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kitchenware")
@RequiredArgsConstructor
public class KitchenwareController {
    private final KitchenwareService kitchenwareService;

    @GetMapping("/{kitchenwareId}")
    public KitchenwareWithCategory getKitchenwareById(@PathVariable Long kitchenwareId){
        return kitchenwareService.readKitchenwareId(kitchenwareId);
    }

    @GetMapping
    public List<KitchenwareWithCategory> getAllKitchenwares(){
        return kitchenwareService.readAllKitchenwares();
    }

    @GetMapping("/categories")
    public List<KitchenwareCategory> getAllKitchenwareCategories(){
        return kitchenwareService.readAllKitchenwareCategories();
    }

    @PostMapping
    public KitchenwareWithCategory createKitchenware(@RequestBody RequestKitchenwareDto requestKitchenwareDto){
        return kitchenwareService.createKitchenware(requestKitchenwareDto);
    }

    @DeleteMapping("/{kitchenwareId}")
    public void deleteKitchenwareById(@PathVariable Long kitchenwareId){
        kitchenwareService.delete(kitchenwareId);
    }

}
