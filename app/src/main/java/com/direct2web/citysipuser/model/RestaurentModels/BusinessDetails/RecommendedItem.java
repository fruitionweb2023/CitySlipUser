package com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails;

import com.google.gson.annotations.SerializedName;

public class RecommendedItem {

    @SerializedName("image")
    private String image;

    @SerializedName("amount")
    private String amount;

    @SerializedName("offer_persantage")
    private String offerPersantage;

    @SerializedName("dish_type")
    private String dishType;

    @SerializedName("description")
    private String description;

    @SerializedName("offer_max_amount")
    private String offerMaxAmount;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("category")
    private String category;

    @SerializedName("max_dish")
    private String maxDish;

    @SerializedName("cuisines")
    private String cuisines;

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

    public String getAmount(){
        return amount;
    }

    public void setOfferPersantage(String offerPersantage){
        this.offerPersantage = offerPersantage;
    }

    public String getOfferPersantage(){
        return offerPersantage;
    }

    public void setDishType(String dishType){
        this.dishType = dishType;
    }

    public String getDishType(){
        return dishType;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setOfferMaxAmount(String offerMaxAmount){
        this.offerMaxAmount = offerMaxAmount;
    }

    public String getOfferMaxAmount(){
        return offerMaxAmount;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public void setMaxDish(String maxDish){
        this.maxDish = maxDish;
    }

    public String getMaxDish(){
        return maxDish;
    }

    public void setCuisines(String cuisines){
        this.cuisines = cuisines;
    }

    public String getCuisines(){
        return cuisines;
    }
}