package com.ester.appleshop.Models;

public class RecommendedModel {
    String name;
    String description;
    String img_url;
    String rating;
    int Price;

    public RecommendedModel(){

    }

    public RecommendedModel(String name, String description, String rating, int price,String img_url) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        Price = price;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
