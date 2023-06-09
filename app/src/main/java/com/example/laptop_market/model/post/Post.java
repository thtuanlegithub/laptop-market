package com.example.laptop_market.model.post;

import java.util.Date;

public class Post{

    private String postID;
    private String laptopID;
    private String accountID;
    private String title;
    private String postStatus;
    private String description;
    private String sellerPhoneNumber;
    private String sellerName;
    private String sellerAddress;
    private Date pushlishTime;
    private String postMainImage;

    public Post(String postID, String laptopID, String accountID, String title, String postStatus, String description, String sellerPhoneNumber, String sellerName, String sellerAddress, Date pushlishTime, String postMainImage) {
        this.postID = postID;
        this.laptopID = laptopID;
        this.accountID = accountID;
        this.title = title;
        this.postStatus = postStatus;
        this.description = description;
        this.sellerPhoneNumber = sellerPhoneNumber;
        this.sellerName = sellerName;
        this.sellerAddress = sellerAddress;
        this.pushlishTime = pushlishTime;
        this.postMainImage = postMainImage;
    }

    public Post() {
    }


    public Date getPushlishTime() {
        return pushlishTime;
    }

    public void setPushlishTime(Date pushlishTime) {
        this.pushlishTime = pushlishTime;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getLaptopID() {
        return laptopID;
    }

    public void setLaptopID(String laptopID) {
        this.laptopID = laptopID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellerPhoneNumber() {
        return sellerPhoneNumber;
    }

    public void setSellerPhoneNumber(String sellerPhoneNumber) {
        this.sellerPhoneNumber = sellerPhoneNumber;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getPostMainImage() {
        return postMainImage;
    }

    public void setPostMainImage(String postMainImage) {
        this.postMainImage = postMainImage;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }
}
