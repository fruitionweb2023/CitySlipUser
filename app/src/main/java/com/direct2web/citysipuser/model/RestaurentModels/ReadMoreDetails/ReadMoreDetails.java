
package com.direct2web.citysipuser.model.RestaurentModels.ReadMoreDetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ReadMoreDetails {

    @SerializedName("business_name")
    private String mBusinessName;
    @SerializedName("contact")
    private String mContact;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("error")
    private Boolean mError;
    @SerializedName("features")
    private String mFeatures;
    @SerializedName("images")
    private List<Image> mImages;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("rating_star")
    private String mRatingStar;
    @SerializedName("tips")
    private String mTips;
    @SerializedName("website")
    private String mWebsite;

    public String getBusinessName() {
        return mBusinessName;
    }

    public void setBusinessName(String businessName) {
        mBusinessName = businessName;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        mContact = contact;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Boolean getError() {
        return mError;
    }

    public void setError(Boolean error) {
        mError = error;
    }

    public String getFeatures() {
        return mFeatures;
    }

    public void setFeatures(String features) {
        mFeatures = features;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public void setImages(List<Image> images) {
        mImages = images;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getRatingStar() {
        return mRatingStar;
    }

    public void setRatingStar(String ratingStar) {
        mRatingStar = ratingStar;
    }

    public String getTips() {
        return mTips;
    }

    public void setTips(String tips) {
        mTips = tips;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

}
