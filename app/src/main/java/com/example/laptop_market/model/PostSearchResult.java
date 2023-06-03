package com.example.laptop_market.model;

public class PostSearchResult {
    private String id;
    private int imgPostSearchResult;
    private String title;
    private String price;
    private String address;

    public PostSearchResult(String id, int imgPostSearchResult, String title, String price, String address) {
        this.id = id;
        this.imgPostSearchResult = imgPostSearchResult;
        this.title = title;
        this.price = price;
        this.address = address;
    }
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
    public int getImgPostSearchResult() {
        return imgPostSearchResult;
    }

    public void setImgPostSearchResult(int imgPostSearchResult) {
        this.imgPostSearchResult = imgPostSearchResult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
