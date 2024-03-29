package com.example.mycollections.Model;

import android.graphics.Bitmap;

public class Place {

    private long id;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private Bitmap image;

    public Place(String title, String author, String publisher, String description, Bitmap image) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.image = image;
    }

    public Place(long id, String title, String author, String publisher, String description, Bitmap image) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


}
