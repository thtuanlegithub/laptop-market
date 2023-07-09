package com.example.laptop_market.model.account;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Account implements Serializable {
    private String accountID;
    private String email;
    private String password;
    private String accountName;
    private String address;
    private Double rating;
    private String phoneNumber;
    private String description;
    private boolean isFinishData;
    private ArrayList<String> publishPosts;
    private ArrayList<String> savedPosts;
    private ArrayList<String> cartItems;
    private ArrayList<String> ratingOrders;
    private ArrayList<String> sellOrders;
    private ArrayList<String> buyOrders;
    private String avatar;

    public Account() {
    }

    public Account(String accountID, String email, String password, String accountName, String address, Double rating, String phoneNumber, boolean isFinishData, ArrayList<String> publishPosts, ArrayList<String> savedPosts, ArrayList<String> ratingOrders, ArrayList<String> sellOrders, ArrayList<String> buyOrders, String avatar) {
        this.accountID = accountID;
        this.email = email;
        this.password = password;
        this.accountName = accountName;
        this.address = address;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.isFinishData = isFinishData;
        this.publishPosts = publishPosts;
        this.savedPosts = savedPosts;
        this.ratingOrders = ratingOrders;
        this.sellOrders = sellOrders;
        this.buyOrders = buyOrders;
        this.avatar = avatar;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getPublishPosts() {
        return publishPosts;
    }

    public void setPublishPosts(ArrayList<String> publishPosts) {
        this.publishPosts = publishPosts;
    }

    public ArrayList<String> getSavedPosts() {
        return savedPosts;
    }

    public void setSavedPosts(ArrayList<String> savedPosts) {
        this.savedPosts = savedPosts;
    }

    public ArrayList<String> getRatingOrders() {
        return ratingOrders;
    }

    public void setRatingOrders(ArrayList<String> ratingOrders) {
        this.ratingOrders = ratingOrders;
    }

    public boolean isFinishData() {
        return isFinishData;
    }

    public void setFinishData(boolean finishData) {
        isFinishData = finishData;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }
    public ArrayList<String> getSellOrders() {
        return sellOrders;
    }

    public void setSellOrders(ArrayList<String> sellOrders) {
        this.sellOrders = sellOrders;
    }

    public ArrayList<String> getBuyOrders() {
        return buyOrders;
    }

    public void setBuyOrders(ArrayList<String> buyOrders) {
        this.buyOrders = buyOrders;
    }

    public ArrayList<String> getConversations() {
        return null;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

