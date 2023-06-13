package com.example.laptop_market.model.order;

import com.google.type.DateTime;

public class Order {
    private String orderID;
    private DateTime orderedDate;
    private DateTime finishedDate;
    private String shipAddress;
    private String orderRating;
    private String orderStatus;
    private Double totalAmount;
    private String postID;
    private String buyerID;

    public Order(String orderID, DateTime orderedDate, DateTime finishedDate, String shipAddress, String orderRating, String orderStatus, Double totalAmount, String postID, String buyerID) {
        this.orderID = orderID;
        this.orderedDate = orderedDate;
        this.finishedDate = finishedDate;
        this.shipAddress = shipAddress;
        this.orderRating = orderRating;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.postID = postID;
        this.buyerID = buyerID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public DateTime getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(DateTime orderedDate) {
        this.orderedDate = orderedDate;
    }

    public DateTime getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(DateTime finishedDate) {
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
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
}
