package com.example.laptop_market.model.brand;

import java.net.URI;

public class Brand {
    private String brandID;
    private String brandName;
    private int img;

    public Brand(String brandID, String brandName, int img) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.img = img;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
