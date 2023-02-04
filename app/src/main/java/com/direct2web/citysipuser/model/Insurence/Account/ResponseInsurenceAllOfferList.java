package com.direct2web.citysipuser.model.Insurence.Account;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseInsurenceAllOfferList {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("offer_list")
@Expose
private List<Offer> offerList = null;

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

public List<Offer> getOfferList() {
return offerList;
}

public void setOfferList(List<Offer> offerList) {
this.offerList = offerList;
}

}