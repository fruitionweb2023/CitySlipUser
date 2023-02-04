package com.direct2web.citysipuser.model.DoctorModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slider {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("slider_image")
    @Expose
    private String image;

    @SerializedName("slider_cat_id")
    @Expose
    private String sliderCatId;

    @SerializedName("redirect_id")
    @Expose
    private String redirectId;

    public String getSliderCatId() {
        return sliderCatId;
    }

    public void setSliderCatId(String sliderCatId) {
        this.sliderCatId = sliderCatId;
    }

    public String getRedirectId() {
        return redirectId;
    }

    public void setRedirectId(String redirectId) {
        this.redirectId = redirectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}