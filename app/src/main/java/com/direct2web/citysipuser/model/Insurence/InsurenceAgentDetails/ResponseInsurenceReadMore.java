package com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInsurenceReadMore {

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
@SerializedName("location")
@Expose
private String location;
@SerializedName("contact")
@Expose
private Object contact;
@SerializedName("website")
@Expose
private Object website;
@SerializedName("features")
@Expose
private String features;
@SerializedName("tips")
@Expose
private String tips;

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

public String getLocation() {
return location;
}

public void setLocation(String location) {
this.location = location;
}

public Object getContact() {
return contact;
}

public void setContact(Object contact) {
this.contact = contact;
}

public Object getWebsite() {
return website;
}

public void setWebsite(Object website) {
this.website = website;
}

public String getFeatures() {
return features;
}

public void setFeatures(String features) {
this.features = features;
}

public String getTips() {
return tips;
}

public void setTips(String tips) {
this.tips = tips;
}

}