package com.example.laptop_market.utils.tables;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchFilterPost implements Serializable {
    public static final String SEARCH_NAME = "search_filter_post";
    private String searchPost;
    private ArrayList<String> listBrandName;
    private ArrayList<String> listGuarantee;
    private ArrayList<String> listCPU;
    private ArrayList<String> listRam;
    private ArrayList<String> listHardDrive;
    private ArrayList<String> listHardDriveSize;
    private ArrayList<String> listGraphics;
    private ArrayList<String> listScreenSize;
    private int minimumPrice;
    private int maximumPrice;

    public SearchFilterPost() {
        listBrandName = new ArrayList<>();
        listGuarantee = new ArrayList<>();
        listCPU = new ArrayList<>();
        listRam = new ArrayList<>();
        listHardDrive = new ArrayList<>();
        listHardDriveSize = new ArrayList<>();
        listGraphics = new ArrayList<>();
        listScreenSize = new ArrayList<>();
        minimumPrice = 0;
        maximumPrice = 50000000;
    }

    public String getSearchPost() {
        return searchPost;
    }

    public void setSearchPost(String searchPost) {
        this.searchPost = searchPost;
    }

    public ArrayList<String> getListScreenSize() {
        return listScreenSize;
    }

    public void setListScreenSize(ArrayList<String> listScreenSize) {
        this.listScreenSize = listScreenSize;
    }

    public ArrayList<String> getListBrandName() {
        return listBrandName;
    }

    public void setListBrandName(ArrayList<String> listBrandName) {
        this.listBrandName = listBrandName;
    }

    public ArrayList<String> getListGuarantee() {
        return listGuarantee;
    }

    public void setListGuarantee(ArrayList<String> listGuarantee) {
        this.listGuarantee = listGuarantee;
    }

    public ArrayList<String> getListCPU() {
        return listCPU;
    }

    public void setListCPU(ArrayList<String> listCPU) {
        this.listCPU = listCPU;
    }

    public ArrayList<String> getListRam() {
        return listRam;
    }

    public void setListRam(ArrayList<String> listRam) {
        this.listRam = listRam;
    }

    public ArrayList<String> getListHardDrive() {
        return listHardDrive;
    }

    public void setListHardDrive(ArrayList<String> listHardDrive) {
        this.listHardDrive = listHardDrive;
    }

    public ArrayList<String> getListHardDriveSize() {
        return listHardDriveSize;
    }

    public void setListHardDriveSize(ArrayList<String> listHardDriveSize) {
        this.listHardDriveSize = listHardDriveSize;
    }

    public ArrayList<String> getListGraphics() {
        return listGraphics;
    }

    public void setListGraphics(ArrayList<String> listGraphics) {
        this.listGraphics = listGraphics;
    }

    public int getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(int minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public int getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(int maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

}
