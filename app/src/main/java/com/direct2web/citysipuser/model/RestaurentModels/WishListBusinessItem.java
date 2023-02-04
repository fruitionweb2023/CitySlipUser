package com.direct2web.citysipuser.model.RestaurentModels;

import com.google.gson.annotations.SerializedName;

public class WishListBusinessItem{
	@SerializedName("business_name")
	private String businessName;

	@SerializedName("distance")
	private String distance;

	@SerializedName("distance_time")
	private String distanceTime;

	@SerializedName("offer_per")
	private String offerPer;

	@SerializedName("description")
	private String description;

	@SerializedName("photo")
	private String photo;

	@SerializedName("id")
	private String id;

	@SerializedName("type")
	private String type;

	@SerializedName("offer_max")
	private String offerMax;

	@SerializedName("rating_star")
	private String ratingStar;

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

	public void setDistanceTime(String distanceTime){
		this.distanceTime = distanceTime;
	}

	public String getDistanceTime(){
		return distanceTime;
	}

	public void setOfferPer(String offerPer){
		this.offerPer = offerPer;
	}

	public String getOfferPer(){
		return offerPer;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setOfferMax(String offerMax){
		this.offerMax = offerMax;
	}

	public String getOfferMax(){
		return offerMax;
	}

	public void setRatingStar(String ratingStar){
		this.ratingStar = ratingStar;
	}

	public String getRatingStar(){
		return ratingStar;
	}
}
