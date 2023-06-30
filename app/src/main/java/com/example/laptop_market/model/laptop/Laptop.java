package com.example.laptop_market.model.laptop;

import android.graphics.Bitmap;
import android.net.Uri;

import java.net.URI;
import java.util.ArrayList;

public class Laptop {
    private String laptopName;
    private String brandID;
    private Double price;
    private String status;
    private String cpu;
    private String ram;
    private String hardDrive;
    private String hardDriveSize;
    private String graphics;
    private String screenSize;
    private String guarantee;
    private String origin;
    private ArrayList<Uri> listImages;
    public Laptop()
    {

    }

    public Laptop(String laptopName, String brandID, Double price, String status, String cpu, String ram, String hardDrive, String hardDriveSize, String graphics, String screenSize, String guarantee, String origin, ArrayList<Uri> listImages) {
        this.laptopName = laptopName;
        this.brandID = brandID;
        this.price = price;
        this.status = status;
        this.cpu = cpu;
        this.ram = ram;
        this.hardDrive = hardDrive;
        this.hardDriveSize = hardDriveSize;
        this.graphics = graphics;
        this.screenSize = screenSize;
        this.guarantee = guarantee;
        this.origin = origin;
        this.listImages = listImages;
    }

    public String getLaptopName() {
        return laptopName;
    }

    public void setLaptopName(String laptopName) {
        this.laptopName = laptopName;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
    }

    public String getHardDriveSize() {
        return hardDriveSize;
    }

    public void setHardDriveSize(String hardDriveSize) {
        this.hardDriveSize = hardDriveSize;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public ArrayList<Uri> getImgLists() {
        return listImages;
    }

    public void setImgLists(ArrayList<Uri> listImages) {
        this.listImages = listImages;
    }
}



