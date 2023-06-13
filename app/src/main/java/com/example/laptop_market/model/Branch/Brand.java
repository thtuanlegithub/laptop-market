package com.example.laptop_market.model.Branch;

public class Brand {
    private int type;
    private int image;
    private String name;
    public Brand(int image, String name, int type){
        this.image = image;
        this.name = name;
        this.type = type;
    }
    public Brand(String name, int type){
        this.name = name;
        this.type = type;
        this.image = 700052;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
