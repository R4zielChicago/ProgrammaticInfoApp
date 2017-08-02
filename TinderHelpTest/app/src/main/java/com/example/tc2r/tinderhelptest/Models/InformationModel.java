package com.example.tc2r.tinderhelptest.Models;

import java.util.ArrayList;

import static android.R.attr.description;

/**
 * Created by TC2R on 8/1/17.
 */

public class InformationModel {
  // name variables better
    String description, imageUrl;



public InformationModel(){
}

    public InformationModel(String description) {
        this.description = description;
    }
    public InformationModel(String description, String imagesUrl) {
        this.description = description;
        this.imageUrl = imagesUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
