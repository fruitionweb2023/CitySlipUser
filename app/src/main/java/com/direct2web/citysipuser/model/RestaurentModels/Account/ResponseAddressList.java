package com.direct2web.citysipuser.model.RestaurentModels.Account;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAddressList {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("address_list")
@Expose
private List<Address> addressList = null;

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

public List<Address> getAddressList() {
return addressList;
}

public void setAddressList(List<Address> addressList) {
this.addressList = addressList;
}

}