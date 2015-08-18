package com.example.android.unsplash.data.model;

/**
 * Model class representing data returned from unsplash.it
 */
public class Photo {

    /*{
        "format": "jpeg",
        "width": 5616,
        "height": 3744,
        "filename": "0000_yC-Yzbqy7PY.jpeg",
        "id": 0,
        "author": "Alejandro Escamilla",
        "author_url": "https://unsplash.com/alejandroescamilla",
        "post_url": "https://unsplash.com/photos/yC-Yzbqy7PY"
    }*/

    public final String format;
    public final int width;
    public final int height;
    public final String filename;
    public final long id;
    public final String author;
    public final String author_url;
    public final String post_url;

    public Photo(String format,
                 int width,
                 int height,
                 String filename,
                 long id,
                 String author,
                 String author_url,
                 String post_url) {
        this.format = format;
        this.width = width;
        this.height = height;
        this.filename = filename;
        this.id = id;
        this.author = author;
        this.author_url = author_url;
        this.post_url = post_url;
    }

}
