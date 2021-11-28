package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.kitchenware.*;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareCategory;
import com.example.persimmoncocktails.models.kitchenware.KitchenwareWithCategory;
import com.example.persimmoncocktails.services.KitchenwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kitchenware")
@PreAuthorize("isAuthenticated")
@RequiredArgsConstructor
public class KitchenwareController {
    private final KitchenwareService kitchenwareService;

    @PreAuthorize("hasAuthority('content:update')")
    @GetMapping("/{kitchenwareId}")
    public KitchenwareWithCategory getKitchenwareById(@PathVariable Long kitchenwareId) {
        return kitchenwareService.readKitchenwareId(kitchenwareId);
    }

    @GetMapping("/active")
    public List<ResponseKitchenwareDto> getAllActiveKitchenware() {
        return kitchenwareService.readAllActiveKitchenwares();
    }

    @GetMapping("/active/{kitchenwareId}")
    public ResponseKitchenwareDto getActiveKitchenwareById(@PathVariable Long kitchenwareId) {
        return kitchenwareService.readActiveKitchenwareId(kitchenwareId);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @GetMapping
    public List<KitchenwareWithCategory> getAllKitchenwares() {
        return kitchenwareService.readAllKitchenwares();
    }

    @GetMapping("/categories")
    public List<KitchenwareCategory> getAllKitchenwareCategories() {
        return kitchenwareService.readAllKitchenwareCategories();
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PostMapping
    public KitchenwareWithCategory createKitchenware(@RequestBody RequestKitchenwareDto requestKitchenwareDto) {
        return kitchenwareService.createKitchenware(requestKitchenwareDto);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/update-name")
    public void updateName(@RequestBody RequestUpdateKitchenwareNameDto updateKitchenwareNameDto) {
        kitchenwareService.updateName(updateKitchenwareNameDto.getKitchenwareId(), updateKitchenwareNameDto.getName());
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/update-photo")
    public void updatePhoto(@RequestBody RequestUpdateKitchenwarePhotoDto updateKitchenwarePhotoDto) {
        kitchenwareService.updatePhoto(updateKitchenwarePhotoDto.getKitchenwareId(),
                updateKitchenwarePhotoDto.getKitchenwarePhotoId());
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/update-category")
    public void updateCategory(@RequestBody RequestUpdateKitchenwareCategoryDto updateKitchenwareCategoryDto) {
        kitchenwareService.updateKitchenwareCategory(updateKitchenwareCategoryDto.getKitchenwareId(),
                updateKitchenwareCategoryDto.getKitchenwareCategoryId());
    }


    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/deactivate/{kitchenwareId}")
    public void deactivateKitchenware(@PathVariable Long kitchenwareId) {
        kitchenwareService.deactivate(kitchenwareId);
    }

    @PreAuthorize("hasAuthority('content:update')")
    @PatchMapping("/activate/{kitchenwareId}")
    public void activateKitchenware(@PathVariable Long kitchenwareId) {
        kitchenwareService.activate(kitchenwareId);
    }
}
