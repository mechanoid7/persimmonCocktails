package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.kitchenware.KitchenwareResponseDto;
import com.example.persimmoncocktails.dtos.kitchenware.RequestKitchenwareDto;
import com.example.persimmoncocktails.services.KitchenwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kitchenware")
@RequiredArgsConstructor
public class KitchenwareController {
    private final KitchenwareService kitchenwareService;

    @GetMapping("/{kitchenwareId}")
    public KitchenwareResponseDto getKitchenwareById(@PathVariable Long kitchenwareId){
        return kitchenwareService.readKitchenwareId(kitchenwareId);
    }

    @PostMapping()
    public KitchenwareResponseDto createKitchenware(@RequestBody RequestKitchenwareDto requestKitchenwareDto){
        return kitchenwareService.createKitchenware(requestKitchenwareDto);
    }

    @DeleteMapping("/{kitchenwareId}")
    public void deleteKitchenwareById(@PathVariable Long kitchenwareId){
        kitchenwareService.delete(kitchenwareId);
    }

}
