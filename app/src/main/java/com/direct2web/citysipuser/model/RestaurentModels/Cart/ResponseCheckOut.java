package com.direct2web.citysipuser.model.RestaurentModels.Cart;

import com.google.gson.annotations.SerializedName;

public class ResponseCheckOut{

    @SerializedName("eta")
    private String eta;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("order_id")
    private String orderId;

    @SerializedName("status")
    private String status;

    public void setEta(String eta){
        this.eta = eta;
    }

    public String getEta(){
        return eta;
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

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public String getOrderId(){
        return orderId;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}