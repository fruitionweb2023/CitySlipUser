package com.direct2web.citysipuser.model.Insurence.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseInsurenceFavourite {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("wish_list_business")
@Expose
private List<WishListBusiness> wishListBusiness = null;

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

public List<WishListBusiness> getWishListBusiness() {
return wishListBusiness;
}

public void setWishListBusiness(List<WishListBusiness> wishListBusiness) {
this.wishListBusiness = wishListBusiness;
}

}