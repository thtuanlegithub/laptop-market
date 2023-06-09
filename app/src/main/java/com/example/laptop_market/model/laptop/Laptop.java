package com.example.laptop_market.model.laptop;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.firestore.Exclude;

import java.net.URI;
import java.util.ArrayList;

public class Laptop {
    private String laptopName;
    private String brandID;
    private int price;
    private String cpu;
    private String ram;
    private String hardDrive;
    private String hardDriveSize;
    private String graphics;
    private String screenSize;
    private String guarantee;
    private String origin;
    private int numOfImage;
    @Exclude
    private ArrayList<Uri> listImages;
    @Exclude
    private ArrayList<Bitmap> listDownloadImages;
    public Laptop()
    {

    }

    public Laptop(String laptopName, String brandID, int price, String cpu, String ram, String hardDrive, String hardDriveSize, String graphics, String screenSize, String guarantee, String origin, ArrayList<Uri> listImages) {
        this.laptopName = laptopName;
        this.brandID = brandID;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public ArrayList<Bitmap> getListDownloadImages() {
        return listDownloadImages;
    }

    public void setListDownloadImages(ArrayList<Bitmap> listDownloadImages) {
        this.listDownloadImages = listDownloadImages;
    }

    public int getNumOfImage() {
        return numOfImage;
    }

    public void setNumOfImage(int numOfImage) {
        this.numOfImage = numOfImage;
    }
    public boolean checkDataNull() {
        if (laptopName == null || brandID == null || cpu == null || ram == null ||
                hardDrive == null || hardDriveSize == null || graphics == null ||
                screenSize == null || guarantee == null ) {
            return true;  // có ít nhất một thuộc tính bằng null
        }
        return false;  // tất cả các thuộc tính đều không bằng null
    }
}



