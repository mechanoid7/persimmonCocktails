package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
//@PreAuthorize("isAuthenticated")
@RequestMapping("/image")
public class ImagesController {

    private final ImageService imageService;

    @Autowired
    public ImagesController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PreAuthorize("isAuthenticated")
    @PostMapping(value = "/upload")
    public void upload(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        System.out.println(">>>FILENAME:"+multipartFile.getName());
        imageService.upload(personId, multipartFile);
    }
}
