package com.example.laptop_market.model.brand;

import java.net.URI;

public class    Brand {
    private String brandID;
    private String brandName;
    private int img;
    private int type;
    public Brand(String brandID, String brandName, int type) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.type = type;
    }

    public Brand(int img,String brandName, int type) {
        this.brandName = brandName;
        this.img = img;
        this.type = type;
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
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
