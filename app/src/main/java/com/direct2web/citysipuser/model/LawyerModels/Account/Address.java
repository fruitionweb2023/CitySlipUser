package com.direct2web.citysipuser.model.LawyerModels.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

@SerializedName("id")
@Expose
private String id;
@SerializedName("customer_id")
@Expose
private String customerId;
@SerializedName("address_line_1")
@Expose
private String addressLine1;
@SerializedName("address_line_2")
@Expose
private String addressLine2;
@SerializedName("latitude")
@Expose
private String latitude;
@SerializedName("longitude")
@Expose
private String longitude;
@SerializedName("address_as")
@Expose
private String addressAs;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getCustomerId() {
return customerId;
}

public void setCustomerId(String customerId) {
this.customerId = customerId;
}

public String getAddressLine1() {
return addressLine1;
}

public void setAddressLine1(String addressLine1) {
this.addressLine1 = addressLine1;
}

public String getAddressLine2() {
return addressLine2;
}

public void setAddressLine2(String addressLine2) {
this.addressLine2 = addressLine2;
}

public String getLatitude() {
return latitude;
}

public void setLatitude(String latitude) {
this.latitude = latitude;
}

public String getLongitude() {
return longitude;
}

public void setLongitude(String longitude) {
this.longitude = longitude;
}

public String getAddressAs() {
return addressAs;
}

public void setAddressAs(String addressAs) {
this.addressAs = addressAs;
}

}