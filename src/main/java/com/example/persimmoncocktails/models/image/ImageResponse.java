package com.example.persimmoncocktails.models.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ImageResponse {
    private Data data;
    private boolean success;
    private int status;

    public String getUrlFull() {
        return data.image.url;
    }

    public String getUrlMiddle() {
        return data.display_url;
    }

    public String getUrlThumb() {
        return data.thumb.url;
    }

    public String getUrlDelete() {
        return data.delete_url;
    }

    @Override
    public String toString() {
        String result = "Success:" + success + ", " + status + "\n";
        result += "Filename:" + data.image.filename +
                ";\nurl:" + data.image.url +
                ";\nthumb_url:" + data.thumb.url +
                ";\ndisplay_url:" + data.display_url;
        return result;
    }
}

class Image {
    public String filename;
    public String name;
    public String mime;
    public String extension;
    public String url;
}

class Thumb {
    public String filename;
    public String name;
    public String mime;
    public String extension;
    public String url;
}

class Data {
    public String id;
    public String title;
    public String url_viewer;
    public String url;
    public String display_url;
    public int size;
    public String time;
    public String expiration;
    public Image image;
    public Thumb thumb;
    public String delete_url;
}


