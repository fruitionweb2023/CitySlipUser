package com.direct2web.citysipuser.model.DoctorModels.HospitalDetails;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDoctorTime {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("slot_list")
@Expose
private List<Slot> slotList = null;

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

public List<Slot> getSlotList() {
return slotList;
}

public void setSlotList(List<Slot> slotList) {
this.slotList = slotList;
}

}