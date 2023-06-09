package com.example.laptop_market.view.adapters.PostSearchResult;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.laptop_market.model.post.Post;

import java.io.Serializable;

public class PostSearchResult implements Serializable {
    private transient Bitmap image;
    private String laptopName;
    private String userId;
    private String laptopId;
    private String postId;
    private String title;
    private String address;
    private double price;
    private String postStatus;

    public String getAccountId() {
        return userId;
    }

    public void setAccountId(String userId) {
        this.userId = userId;
    }

    public String getLaptopId() {
        return laptopId;
    }

    public void setLaptopId(String laptopId) {
        this.laptopId = laptopId;
    }

    public PostSearchResult() {
    }

    public PostSearchResult(String title, double price, String address){
        this.title = title;
        this.price = price;
        this.address = address;
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }
}
