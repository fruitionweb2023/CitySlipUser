package com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseBusinessDetails{

    @SerializedName("offer")
    private List<Offer> offer;

    @SerializedName("business_name")
    private String businessName;

    @SerializedName("distance")
    private String distance;

    @SerializedName("menu_list")
    private List<MenuList> menuList;

    @SerializedName("distance_time")
    private String distanceTime;

    @SerializedName("description")
    private String description;

    @SerializedName("wish_list")
    private String wishList;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("recommended_item")
    private List<RecommendedItem> recommendedItem;

    @SerializedName("rating_star")
    private String ratingStar;

    public void setOffer(List<Offer> offer){
        this.offer = offer;
    }

    public List<Offer> getOffer(){
        return offer;
    }

    public void setBusinessName(String businessName){
        this.businessName = businessName;
    }

    public String getBusinessName(){
        return businessName;
    }

    public void setDistance(String distance){
        this.distance = distance;
    }

    public String getDistance(){
        return distance;
    }

    public void setMenuList(List<MenuList> menuList){
        this.menuList = menuList;
    }

    public List<MenuList> getMenuList(){
        return menuList;
    }

    public void setDistanceTime(String distanceTime){
        this.distanceTime = distanceTime;
    }

    public String getDistanceTime(){
        return distanceTime;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setWishList(String wishList){
        this.wishList = wishList;
    }

    public String getWishList(){
        return wishList;
    }

    public void setError(boolean error){
        this.error = error;
    }

    public boolean isError(){
        return error;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setRecommendedItem(List<RecommendedItem> recommendedItem){
        this.recommendedItem = recommendedItem;
    }

    public List<RecommendedItem> getRecommendedItem(){
        return recommendedItem;
    }

    public void setRatingStar(String ratingStar){
        this.ratingStar = ratingStar;
    }

    public String getRatingStar(){
        return ratingStar;
    }
}