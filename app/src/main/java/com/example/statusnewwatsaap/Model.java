package com.example.statusnewwatsaap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;

public class Model implements Serializable {
    String  image;

    public Model(String id, String title, String desc) {

    }



    public String getImage() {
        return image;
    }

    public void setImages(String image) {
        this.image = image;
    }

}
