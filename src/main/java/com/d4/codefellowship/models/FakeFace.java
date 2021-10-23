package com.d4.codefellowship.models;

public class FakeFace {

    private int age;
    private String date_added;
    private String filename;
    private String gender;
    private String image_url;
    private String last_served;
    private String source;

    public FakeFace() {
    }

    public FakeFace(int age, String date_added, String filename, String gender, String image_url, String last_served, String source) {
        this.age = age;
        this.date_added = date_added;
        this.filename = filename;
        this.gender = gender;
        this.image_url = image_url;
        this.last_served = last_served;
        this.source = source;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
