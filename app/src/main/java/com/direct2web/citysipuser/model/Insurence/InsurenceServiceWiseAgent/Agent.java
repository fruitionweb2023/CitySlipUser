package com.direct2web.citysipuser.model.Insurence.InsurenceServiceWiseAgent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Agent {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("contact")
@Expose
private String contact;
@SerializedName("information")
@Expose
private String information;
@SerializedName("profile")
@Expose
private String profile;
@SerializedName("rating_star")
@Expose
private String ratingStar;
@SerializedName("distance_time")
@Expose
private String distanceTime;
@SerializedName("distance")
@Expose
private String distance;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getContact() {
return contact;
}

public void setContact(String contact) {
this.contact = contact;
}

public String getInformation() {
return information;
}

public void setInformation(String information) {
this.information = information;
}

public String getProfile() {
return profile;
}

public void setProfile(String profile) {
this.profile = profile;
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

}