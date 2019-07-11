package com.example.whjth.swipelayout_demo;

/**
 * Created by whjth on 2017/6/16.
 */

public class Person {
    private String name;
    private int ImageId;

    public Person(String name, int ImageId){
        this.name = name;
        this.ImageId = ImageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return ImageId;
    }
}
