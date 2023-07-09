package com.example.laptop_market.model.filter;

import java.io.Serializable;
import java.util.List;

public class Filter implements Serializable {
    private boolean isFiltered;
    private String name; //default name
    private String tag;
    private int image;
    private List<String> currentSelectedList;
    private String currentDisplayString;

    public Filter(String name) {
        this.name = name;
        this.image = 700052;
        this.tag = name;
    }
    public Filter(List<String> currentSelectedList){
        this.currentSelectedList = currentSelectedList;
    }
    public Filter(int image, String name){
        this.image = image;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean getIsFiltered() {
        return isFiltered;
    }

    public void setFiltered(boolean filtered) {
        isFiltered = filtered;
    }
}
