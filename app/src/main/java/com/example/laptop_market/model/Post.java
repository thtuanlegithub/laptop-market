package com.example.laptop_market.model;

public class Post {
    private String postID;
    private String laptopName;
    private String status;
    private String brandName;
    private String CPU;
    private String RAM;
    private String hardDriveSize;
    private String hardDrive;
    private String graphics;
    private String screenSize;
    private String guarantee;
    private String origin;
    private String price;
    private String description;
    private String accountID;
    private String address;
    private int img;

    public Post(String postID, String laptopName, String status, String brandName, String cpu, String ram, String hardDriveSize, String hardDrive, String graphics, String screenSize, String guarantee, String origin, String price, String description, String accountID, int img, String address) {
        this.postID = postID;
        this.laptopName = laptopName;
        this.status = status;
        this.brandName = brandName;
        CPU = cpu;
        RAM = ram;
        this.hardDriveSize = hardDriveSize;
        this.hardDrive = hardDrive;
        this.graphics = graphics;
        this.screenSize = screenSize;
        this.guarantee = guarantee;
        this.origin = origin;
        this.price = price;
        this.description = description;
        this.accountID = accountID;
        this.img = img;
        this.address = address;
    }
    public Post(String postID, String laptopName, String price, int img, String address){
        this.postID = postID;
        this.laptopName = laptopName;
        this.price = price;
        this.img = img;
        this.address = address;
    }
    public Post(){

    }
    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getLaptopName() {
        return laptopName;
    }

    public void setLaptopName(String laptopName) {
        this.laptopName = laptopName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public String getRAM() {
        return RAM;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }

    public String getHardDriveSize() {
        return hardDriveSize;
    }

    public void setHardDriveSize(String hardDriveSize) {
        this.hardDriveSize = hardDriveSize;
    }

    public String getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
