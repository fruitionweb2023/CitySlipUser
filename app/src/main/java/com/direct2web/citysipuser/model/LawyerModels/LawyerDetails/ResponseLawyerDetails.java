package com.direct2web.citysipuser.model.LawyerModels.LawyerDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLawyerDetails {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("business_name")
@Expose
private String businessName;
@SerializedName("description")
@Expose
private String description;
@SerializedName("rating_star")
@Expose
private String ratingStar;
@SerializedName("distance_time")
@Expose
private String distanceTime;
@SerializedName("distance")
@Expose
private String distance;
@SerializedName("wish_list")
@Expose
private String wishList;
@SerializedName("offer")
@Expose
private List<Offer> offer = null;
@SerializedName("service_list")
@Expose
private List<Service> serviceList = null;

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

public String getBusinessName() {
return businessName;
}

public void setBusinessName(String businessName) {
this.businessName = businessName;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getRatingStar() {
return ratingStar;
}

public void setRatingStar(String ratingStar) {
this.ratingStar = ratingStar;
}

public String getDistanceTime() {
return distanceTime;
}

public void setDistanceTime(String distanceTime) {
this.distanceTime = distanceTime;
}

public String getDistance() {
return distance;
}

public void setDistance(String distance) {
this.distance = distance;
}

public String getWishList() {
return wishList;
}

public void setWishList(String wishList) {
this.wishList = wishList;
}

public List<Offer> getOffer() {
return offer;
}

public void setOffer(List<Offer> offer) {
this.offer = offer;
}

public List<Service> getServiceList() {
return serviceList;
}

public void setServiceList(List<Service> serviceList) {
this.serviceList = serviceList;
}

}