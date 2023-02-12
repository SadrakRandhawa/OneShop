package com.example.oneshop;

public class fetchElectronicModel {

    public String id,image,name,quality,warrenty,price;

    public fetchElectronicModel() {
    }

    public fetchElectronicModel(String id, String image, String name, String quality, String warrenty, String price) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.quality = quality;
        this.warrenty = warrenty;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getWarrenty() {
        return warrenty;
    }

    public void setWarrenty(String warrenty) {
        this.warrenty = warrenty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
