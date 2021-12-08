package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import com.example.persimmoncocktails.models.image.ImageResponse;

public interface ImageDao {
    void save(Long personId, ImageResponse imageResponse);

    ImageResponseDto getById(Long imageId);

    Boolean isExistsById(Long imageId);

    String deleteById(Long imageId);

    String deleteByIdByOwner(Long imageId, Long personId);

    String getDeleteUrlById(Long imageId);

    Boolean isPersonHasImage(Long personId, Long imageId);
}