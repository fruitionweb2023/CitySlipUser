package com.direct2web.citysipuser.model.RestaurentModels.Cart;

import com.google.gson.annotations.SerializedName;

public class OfferListItem{

    @SerializedName("coupn_code")
    private String coupnCode;

    @SerializedName("min_amount")
    private String minAmount;

    @SerializedName("percentage")
    private String percentage;

    @SerializedName("max_amount")
    private String maxAmount;

    @SerializedName("id")
    private String id;

    @SerializedName("terms_condition")
    private String termsCondition;

    public void setCoupnCode(String coupnCode){
        this.coupnCode = coupnCode;
    }

    public String getCoupnCode(){
        return coupnCode;
    }

    public void setMinAmount(String minAmount){
        this.minAmount = minAmount;
    }

    public String getMinAmount(){
        return minAmount;
    }

    public void setPercentage(String percentage){
        this.percentage = percentage;
    }

    public String getPercentage(){
        return percentage;
    }

    public void setMaxAmount(String maxAmount){
        this.maxAmount = maxAmount;
    }

    public String getMaxAmount(){
        return maxAmount;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setTermsCondition(String termsCondition){
        this.termsCondition = termsCondition;
    }

    public String getTermsCondition(){
        return termsCondition;
    }
}