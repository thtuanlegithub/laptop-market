package com.example.laptop_market.view.adapters.Buy;

import android.graphics.Bitmap;

import java.io.Serializable;

public class BuyOrder implements Serializable {
    private transient Bitmap image;
    private String laptopName;
    private String price;
    private String address;
    private String orderId;
    private String sellerId;
    public BuyOrder () {

    }

    public BuyOrder(Bitmap image, String laptopName, String price, String address, String orderId, String sellerId) {
        this.image = image;
        this.laptopName = laptopName;
        this.price = price;
        this.address = address;
        this.orderId = orderId;
        this.sellerId = sellerId;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getLaptopName() {
        return laptopName;
    }

    public void setLaptopName(String laptopName) {
        this.laptopName = laptopName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}