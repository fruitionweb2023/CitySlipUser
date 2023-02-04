package com.direct2web.citysipuser.model.DoctorModels.HospitalDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Slot {

@SerializedName("slot")
@Expose
private String slot;

public String getSlot() {
return slot;
}

public void setSlot(String slot) {
this.slot = slot;
}

}