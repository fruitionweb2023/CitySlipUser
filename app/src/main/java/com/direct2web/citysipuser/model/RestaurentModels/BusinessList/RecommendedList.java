
package com.direct2web.citysipuser.model.RestaurentModels.BusinessList;

import com.google.gson.annotations.SerializedName;

public class RecommendedList {

    @SerializedName("business_name")
    private String mBusinessName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("distance")
    private String mDistance;
    @SerializedName("distance_time")
    private String mDistanceTime;
    @SerializedName("id")
    private String mId;
    @SerializedName("offer_max")
    private String mOfferMax;
    @SerializedName("offer_per")
    private String mOfferPer;
    @SerializedName("photo")
    private String mPhoto;
    @SerializedName("rating_star")
    private String mRatingStar;
    @SerializedName("type")
    private String mType;

    public String getBusinessName() {
        return mBusinessName;
    }

    public void setBusinessName(String businessName) {
        mBusinessName = businessName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getDistanceTime() {
        return mDistanceTime;
    }

    public void setDistanceTime(String distanceTime) {
        mDistanceTime = distanceTime;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getOfferMax() {
        return mOfferMax;
    }

    public void setOfferMax(String offerMax) {
        mOfferMax = offerMax;
    }

    public String getOfferPer() {
        return mOfferPer;
    }

    public void setOfferPer(String offerPer) {
        mOfferPer = offerPer;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getRatingStar() {
        return mRatingStar;
    }

    public void setRatingStar(String ratingStar) {
        mRatingStar = ratingStar;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
