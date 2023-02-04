package com.direct2web.citysipuser.model.RestaurentModels.Cart;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseOfferList{

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("offer_list")
    private List<OfferListItem> offerList;

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

    public void setOfferList(List<OfferListItem> offerList){
        this.offerList = offerList;
    }

    public List<OfferListItem> getOfferList(){
        return offerList;
    }
}