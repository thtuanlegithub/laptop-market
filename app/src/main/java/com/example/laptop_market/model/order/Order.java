package com.example.laptop_market.model.order;

import com.google.type.DateTime;

public class Order {
    private String orderID;
    private String orderedDate;
    private String finishedDate;
    private String shipAddress;
    private String orderRating;
    private String orderStatus;
    private String totalAmount;
    private String postID;
    private String buyerID;
    private String sellerID;
    private String buyerName;
    private String buyerPhone;

    public Order (){

    }

    public Order(String orderID, String orderedDate, String finishedDate, String shipAddress, String orderRating, String orderStatus, String totalAmount, String postID, String buyerID, String sellerID, String buyerName, String buyerPhone) {
        this.orderID = orderID;
        this.orderedDate = orderedDate;
        this.finishedDate = finishedDate;
        this.shipAddress = shipAddress;
        this.orderRating = orderRating;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.postID = postID;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(String finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getOrderRating() {
        return orderRating;
    }

    public void setOrderRating(String orderRating) {
        this.orderRating = orderRating;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getSellerID() { return sellerID; }

    public void setSellerID(String sellerID) { this.sellerID = sellerID; }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }
}
