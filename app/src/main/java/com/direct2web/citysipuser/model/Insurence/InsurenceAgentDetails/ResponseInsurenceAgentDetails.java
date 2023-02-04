package com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ResponseInsurenceAgentDetails {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("name")
@Expose
private String name;
@SerializedName("information")
@Expose
private String information;
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

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getInformation() {
return information;
}

public void setInformation(String information) {
this.information = information;
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

public List<Service> getServiceList() {
return serviceList;
}

public void setServiceList(List<Service> serviceList) {
this.serviceList = serviceList;
}

}