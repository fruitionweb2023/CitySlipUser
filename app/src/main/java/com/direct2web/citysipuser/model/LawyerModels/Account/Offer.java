package com.direct2web.citysipuser.model.LawyerModels.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {

@SerializedName("id")
@Expose
private String id;
@SerializedName("coupn_code")
@Expose
private String coupnCode;
@SerializedName("max_amount")
@Expose
private String maxAmount;
@SerializedName("terms_condition")
@Expose
private String termsCondition;
@SerializedName("min_amount")
@Expose
private String minAmount;
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

public String getMaxAmount() {
return maxAmount;
}

public void setMaxAmount(String maxAmount) {
this.maxAmount = maxAmount;
}

public String getTermsCondition() {
return termsCondition;
}

public void setTermsCondition(String termsCondition) {
this.termsCondition = termsCondition;
}

public String getMinAmount() {
return minAmount;
}

public void setMinAmount(String minAmount) {
this.minAmount = minAmount;
}

public String getPercentage() {
return percentage;
}

public void setPercentage(String percentage) {
this.percentage = percentage;
}

}