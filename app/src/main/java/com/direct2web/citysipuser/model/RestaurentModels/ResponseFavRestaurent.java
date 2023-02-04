package com.direct2web.citysipuser.model.RestaurentModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFavRestaurent{
	@SerializedName("error")
	private boolean error;
	@SerializedName("message")
	private String message;

	@SerializedName("wish_list_business")
	private List<WishListBusinessItem> wishListBusiness;

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

	public void setWishListBusiness(List<WishListBusinessItem> wishListBusiness){
		this.wishListBusiness = wishListBusiness;
	}

	public List<WishListBusinessItem> getWishListBusiness(){
		return wishListBusiness;
	}
}