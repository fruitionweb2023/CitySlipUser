package com.direct2web.citysipuser.model.LawyerModels.LawyerDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {

@SerializedName("id")
@Expose
private String id;
@SerializedName("business_id")
@Expose
private String businessId;
@SerializedName("doctor_name")
@Expose
private String doctorName;
@SerializedName("service_name")
@Expose
private String serviceName;
@SerializedName("amount")
@Expose
private String amount;
@SerializedName("description")
@Expose
private String description;
@SerializedName("offer")
@Expose
private String offer;
@SerializedName("image")
@Expose
private String image;
@SerializedName("status")
@Expose
private String status;
@SerializedName("offer_persantage")
@Expose
private String offerPersantage;
@SerializedName("offer_max_amount")
@Expose
private String offerMaxAmount;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getBusinessId() {
return businessId;
}

public void setBusinessId(String businessId) {
this.businessId = businessId;
}

public String getDoctorName() {
return doctorName;
}

public void setDoctorName(String doctorName) {
this.doctorName = doctorName;
}

public String getServiceName() {
return serviceName;
}

public void setServiceName(String serviceName) {
this.serviceName = serviceName;
}

public String getAmount() {
return amount;
}

public void setAmount(String amount) {
this.amount = amount;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getOffer() {
return offer;
}

public void setOffer(String offer) {
this.offer = offer;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getOfferPersantage() {
return offerPersantage;
}

public void setOfferPersantage(String offerPersantage) {
this.offerPersantage = offerPersantage;
}

public String getOfferMaxAmount() {
return offerMaxAmount;
}

public void setOfferMaxAmount(String offerMaxAmount) {
this.offerMaxAmount = offerMaxAmount;
}

}