package com.direct2web.citysipuser.model.Insurence.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishListBusiness {

@SerializedName("id")
@Expose
private String id;
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
@SerializedName("offer_per")
@Expose
private String offerPer;
@SerializedName("offer_max")
@Expose
private String offerMax;
@SerializedName("photo")
@Expose
private String photo;
@SerializedName("type")
@Expose
private String type;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
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

public String getOfferPer() {
return offerPer;
}

public void setOfferPer(String offerPer) {
this.offerPer = offerPer;
}

public String getOfferMax() {
return offerMax;
}

public void setOfferMax(String offerMax) {
this.offerMax = offerMax;
}

public String getPhoto() {
return photo;
}

public void setPhoto(String photo) {
this.photo = photo;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

}