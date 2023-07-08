package com.example.laptop_market.model.account;

import java.io.Serializable;

public class CurrentBuyer implements Serializable {
    private String accountID;
    private String accountName;
    private String address;
    private String phoneNumber;

    public CurrentBuyer() {

    }
    public CurrentBuyer(String accountID, String accountName, String address, String phoneNumber) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
