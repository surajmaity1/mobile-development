package com.example.farmerapp;

public class Users {

    private int mId;
    private String mName;
    private String mImageUrl;
    private String mCollege;
    private Double mRoll;

    public Users(int id, String name, String imageUrl, String college, Double roll){
        mId = id;
        mName = name;
        mImageUrl = imageUrl;
        mCollege = college;
        mRoll = roll;
    }
    public int getId(){return mId;}
    public String getName(){return mName;}
    public String getImageUrl(){return mImageUrl;}
    public String getCollegeName(){return mCollege;}
    public Double getRoll(){return mRoll;}
}
