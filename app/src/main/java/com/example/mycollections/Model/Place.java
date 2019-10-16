package com.example.mycollections.Model;

public class Place {

    private String name;
    private String city;
    private String country;
    private String description;
    private String path;

    public Place(String name, String city, String country, String description, String path) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.description = description;
        this.path = path;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
