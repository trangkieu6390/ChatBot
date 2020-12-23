package com.example.covid_19.DashBoard;

import android.graphics.drawable.Drawable;

public class SymptomsHelperClass {
    int image;
    String title;
    Drawable gradient;

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getGradient() {
        return gradient;
    }

    public SymptomsHelperClass(int image, String title, Drawable gradient) {

        this.image = image;
        this.title = title;
        //this.description = description;
        this.gradient = gradient;

    }
}
