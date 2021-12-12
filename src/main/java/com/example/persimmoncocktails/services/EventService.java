package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.ImageDao;
import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.event.RequestCreateEventDto;
import com.example.persimmoncocktails.dtos.event.ResponseEventDto;
import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.exceptions.NullException;
import com.example.persimmoncocktails.models.image.ImageResponse;
import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class EventService {

    private final ImageDao imageDao;
    private final PersonDao personDao;

    public EventService(ImageDao imageDao, PersonDao personDao) {
        this.imageDao = imageDao;
        this.personDao = personDao;
    }

//    public ImageResponse upload(MultipartFile multipartFile) throws IOException {
//        HttpPost post = new HttpPost("https://api.imgbb.com/1/upload?key=" + this.IMAGE_API_KEY);
//
//        List<NameValuePair> urlParameters = new ArrayList<>();
//        urlParameters.add(new BasicNameValuePair("image", Base64.getEncoder().encodeToString(multipartFile.getBytes())));
//        post.setEntity(new UrlEncodedFormEntity(urlParameters));
//
//        Gson gson = new Gson();
//        String responseString = "";
//
//        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {
//            responseString = EntityUtils.toString(response.getEntity());
//            System.out.println(responseString);
//        }
//
//        ImageResponse imageResponse = gson.fromJson(responseString, ImageResponse.class);
//        System.out.println(">>Uploaded file: " + imageResponse.toString());
//        return imageResponse;
//    }
//
//    public ImageResponseDto saveImage(Long personId, MultipartFile multipartFile) throws IOException {
//        if (!personDao.existsById(personId)) throw new NotFoundException("Person");
//        if (multipartFile == null) throw new NullException("File not received");
//        ImageResponse response = upload(multipartFile);
//        Long imageId = imageDao.save(personId, response);
//        return getImageById(imageId);
//    }

    public ImageResponseDto getImageById(Long imageId) {
//        if (!imageDao.isExistsById(imageId)) throw new NotFoundException("Image"); // removed so as not to spam the front with errors, the picture "not found" will be displayed in front
        if (imageDao.isExistsById(imageId)) {
            return imageDao.getById(imageId);
        } else return null;
    }

    public void deleteImageById(Long imageId) {
        if (!imageDao.isExistsById(imageId)) throw new NotFoundException("Image");
        String deleteUrl = imageDao.deleteById(imageId);
        // if need - code for deleting image from server
    }

    public void deleteImageByIdByOwner(Long imageId, Long personId) {
        if (!imageDao.isPersonHasImage(personId, imageId)) throw new NotFoundException("User image");
        String deleteUrl = imageDao.deleteByIdByOwner(imageId, personId);
        // if need - code for deleting image from server
    }

    public void createEvent(RequestCreateEventDto requestCreateEvent, Long personId) {
    }


    public void disableEvent(Long eventId, Long personId) {
    }

    public List<ResponseEventDto> getMemberEvents(Long personId, Long pageNumber) {
        return null;
    }

    public List<ResponseEventDto> getOwnerEvents(Long personId, Long pageNumber) {
        return null;
    }

    public void updateImage(Long eventId, Long personId, Long imageId) {
    }

    public void inviteToEvent(Long eventId, Long personId, Long cocktailId) {
    }

    public void leaveFromEvent(Long eventId, Long personId, Long cocktailId) {
    }

    public void removeCocktailFromEvent(Long eventId, Long personId, Long cocktailId) {
    }

    public void addCocktailToEvent(Long eventId, Long personId, Long cocktailId) {
    }

    public void declineEvent(Long eventId, Long personId) {
    }

    public void acceptEvent(Long eventId, Long personId) {
    }
}
