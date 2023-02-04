package com.direct2web.citysipuser.model.RestaurentModels.Cart;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseCart{

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("cart")
    private List<CartItem> cart;

    @SerializedName("total_amount")
    private String totel;

    public String getTotel() {
        return totel;
    }

    public void setTotel(String totel) {
        this.totel = totel;
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

    public void setCart(List<CartItem> cart){
        this.cart = cart;
    }

    public List<CartItem> getCart(){
        return cart;
    }
}