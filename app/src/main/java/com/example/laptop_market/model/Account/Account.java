package com.example.laptop_market.model.Account;

import java.util.ArrayList;

public class Account {
    public static final String KEY_TABLE_ACCOUNT= "account";
    public static final String KEY_ID_ACCOUNT = "id_account";
    public static final String KEY_ACCOUNT_NAME = "account_name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CHECK_DATA = "check_data";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_LIST_PUBLISH_POST = "list_publish_post";
    private String idAccount;
    private String name;
    private String email;
    private String password;
    private String gender;

    public Account() {
    }

    public Account(String idAccount, String name, String email, String password, String gender) {
        this.idAccount = idAccount;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public String getID_Account() {
        return idAccount;
    }

    public void setID_Account(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
