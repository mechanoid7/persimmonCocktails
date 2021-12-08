package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.ImageDao;
import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import com.example.persimmoncocktails.mappers.image.ImageMapper;
import com.example.persimmoncocktails.models.image.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@PropertySources({
        @PropertySource("classpath:sql/image_queries.properties"),
        @PropertySource("classpath:var/general.properties")
})
@RequiredArgsConstructor
public class ImageDaoImpl implements ImageDao {

    private final JdbcTemplate jdbcTemplate;
    private final ImageMapper imageMapper;
//    private final CocktailMapper cocktailMapper = new CocktailMapper();

    @Value("${sql_image_add}")
    private String sqlAddImage;
    @Value("${sql_image_delete_by_id}")
    private String sqlDeleteImageById;
    @Value("${sql_image_delete_by_id_by_owner}")
    private String sqlDeleteImageByIdByOwner;
    @Value("${sql_image_get_by_id}")
    private String sqlGetImageById;
    @Value("${sql_image_exists_by_id}")
    private String sqlImageExistsById;
    @Value("${sql_image_get_delete_url}")
    private String sqlGetDeleteUrlById;
    @Value("${sql_image_is_person_has_image}")
    private String sqlIsPersonHasImage;

    @Override
    public void save(Long personId, ImageResponse imageResponse) {
        jdbcTemplate.update(sqlAddImage, personId, imageResponse.getUrlFull(), imageResponse.getUrlMiddle(),
                imageResponse.getUrlThumb(), imageResponse.getUrlDelete());
    }

    @Override
    public ImageResponseDto getById(Long imageId) {
        return jdbcTemplate.queryForObject(sqlGetImageById, imageMapper, imageId);
    }

    @Override
    public Boolean isExistsById(Long imageId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlImageExistsById, Boolean.class, imageId));
    }

    @Override
    public String deleteById(Long imageId) {
        return jdbcTemplate.queryForObject(sqlDeleteImageById, String.class, imageId);
    }

    @Override
    public String deleteByIdByOwner(Long imageId, Long personId) {
        return jdbcTemplate.queryForObject(sqlDeleteImageByIdByOwner, String.class, imageId, imageId, personId);
    }

    @Override
    public String getDeleteUrlById(Long imageId) {
        return jdbcTemplate.queryForObject(sqlGetDeleteUrlById, String.class, imageId);
    }

    @Override
    public Boolean isPersonHasImage(Long personId, Long imageId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlIsPersonHasImage, Boolean.class, imageId, personId));
    }
}
