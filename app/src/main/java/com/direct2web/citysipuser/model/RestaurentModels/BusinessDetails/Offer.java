package com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails;

import com.google.gson.annotations.SerializedName;

public class Offer {

    @SerializedName("coupn_code")
    private String coupnCode;

    @SerializedName("percentage")
    private String percentage;

    @SerializedName("id")
    private String id;

    public void setCoupnCode(String coupnCode){
        this.coupnCode = coupnCode;
    }

    public String getCoupnCode(){
        return coupnCode;
    }

    public void setPercentage(String percentage){
        this.percentage = percentage;
    }

    public String getPercentage(){
        return percentage;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
}