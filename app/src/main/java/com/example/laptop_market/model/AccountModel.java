package com.example.laptop_market.model;

import java.util.ArrayList;

public class AccountModel {
    private String accountID;
    private String email;
    private String password;
    private String accountName;
    private String address;
    private Double rating;
    private ArrayList<String> publishPosts;
    private ArrayList<String> savedPosts;
    private ArrayList<String> cartItems;

    public AccountModel(String accountID, String email, String password, String accountName, String address, Double rating, ArrayList<String> publishPosts, ArrayList<String> savedPosts, ArrayList<String> cartItems) {
        this.accountID = accountID;
        this.email = email;
        this.password = password;
        this.accountName = accountName;
        this.address = address;
        this.rating = rating;
        this.publishPosts = publishPosts;
        this.savedPosts = savedPosts;
        this.cartItems = cartItems;
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

    public ArrayList<String> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<String> cartItems) {
        this.cartItems = cartItems;
    }
}
