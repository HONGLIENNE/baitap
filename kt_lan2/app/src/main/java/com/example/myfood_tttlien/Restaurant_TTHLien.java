package com.example.myfood_tttlien;

public class Restaurant_TTHLien {

    private int resID;
    private String name;
    private String address;
    private String phone;
    private String image;

    public Restaurant_TTHLien(int resID, String name, String address, String phone, String image) {
        this.resID = resID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.image = image;
    }

    public int getResID() {
        return resID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }
}