package com.example.shivani.miete;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by ravi joshi on 10/23/2016.
 */
public class model implements Serializable {
    private String id;
    private String name;
    private String imgpath;
    private String gender;
    private String category;
    private String  contactno;
    private String Location;
    private String advmoney;
    private String rentduo;
    private String rentamo;
    private String uniqueid;
    private Bitmap bitmap;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAdvmoney() {
        return advmoney;
    }

    public void setAdvmoney(String advmoney) {
        this.advmoney = advmoney;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getRentduo() {
        return rentduo;
    }

    public void setRentduo(String rentduo) {
        this.rentduo = rentduo;
    }

    public String getRentamo() {
        return rentamo;
    }

    public void setRentamo(String rentamo) {
        this.rentamo = rentamo;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }



    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
