package com.example.oneshop;

import java.io.Serializable;

public class fetchRecordModel  implements Serializable
{

    public String image,title,detail,price;
    public String id;
    public static final int ItemView_property = 0;
    public static final int ItemView_electronics = 1;
    public static final int ItemView_furniture = 2;
    public int viewType;

    public fetchRecordModel() {
    }

    public fetchRecordModel(String id, String image, String title, String detail, String price, int viewType) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.detail = detail;
        this.price = price;
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
