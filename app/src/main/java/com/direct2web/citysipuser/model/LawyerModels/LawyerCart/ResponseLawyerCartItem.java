package com.direct2web.citysipuser.model.LawyerModels.LawyerCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLawyerCartItem {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("cart")
@Expose
private List<Cart> cart = null;
@SerializedName("total_amount")
@Expose
private Integer totalAmount;

public Boolean getError() {
return error;
}

public void setError(Boolean error) {
this.error = error;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Cart> getCart() {
return cart;
}

public void setCart(List<Cart> cart) {
this.cart = cart;
}

public Integer getTotalAmount() {
return totalAmount;
}

public void setTotalAmount(Integer totalAmount) {
this.totalAmount = totalAmount;
}

}