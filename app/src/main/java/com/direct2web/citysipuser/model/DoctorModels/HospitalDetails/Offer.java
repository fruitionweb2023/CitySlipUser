package com.direct2web.citysipuser.model.DoctorModels.HospitalDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {

@SerializedName("id")
@Expose
private String id;
@SerializedName("coupn_code")
@Expose
private String coupnCode;
@SerializedName("percentage")
@Expose
private String percentage;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getCoupnCode() {
return coupnCode;
}

public void setCoupnCode(String coupnCode) {
this.coupnCode = coupnCode;
}

public String getPercentage() {
return percentage;
}

public void setPercentage(String percentage) {
this.percentage = percentage;
}

}