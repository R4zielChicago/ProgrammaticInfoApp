
package com.example.tc2r.tinderhelptest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChapterModel {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Subtitle")
    @Expose
    private String subtitle;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Layout ID")
    @Expose
    private String layoutID;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ChapterModel() {
    }

    /**
     * 
     * @param title
     * @param description
     * @param layoutID
     * @param subtitle
     * @param image
     * @param iD
     */
    public ChapterModel(String iD, String title, String subtitle, String image, String description, String layoutID) {
        super();
        this.iD = iD;
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
        this.description = description;
        this.layoutID = layoutID;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLayoutID() {
        return layoutID;
    }

    public void setLayoutID(String layoutID) {
        this.layoutID = layoutID;
    }

}
