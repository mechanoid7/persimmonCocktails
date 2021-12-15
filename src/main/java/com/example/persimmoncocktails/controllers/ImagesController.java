package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import com.example.persimmoncocktails.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImagesController {

    private final ImageService imageService;

    @Autowired
    public ImagesController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/get/{imageId}")
    public ImageResponseDto get(@PathVariable Long imageId) {
        return imageService.getImageById(imageId);
    }

    @PreAuthorize("isAuthenticated")
    @DeleteMapping(value = "/owner-delete")
    public void deleteByIdByOwner(@RequestBody Long imageId) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        imageService.deleteImageByIdByOwner(imageId, personId);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @DeleteMapping(value = "/delete")
    public void deleteById(@RequestBody Long imageId) {
        imageService.deleteImageById(imageId);
    }

    @PreAuthorize("isAuthenticated")
    @PostMapping(value = "/upload")
    public ImageResponseDto upload(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return imageService.saveImage(personId, multipartFile);
    }
}
