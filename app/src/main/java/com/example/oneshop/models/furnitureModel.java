package com.example.oneshop.models;

public class furnitureModel {

    String id;
    String name, warrenty,image,price;

    public furnitureModel() {
    }

    public furnitureModel(String id, String price, String image, String name, String warrenty) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.warrenty = warrenty;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWarrenty() {
        return warrenty;
    }

    public void setWarrenty(String warrenty) {
        this.warrenty = warrenty;
    }
}
