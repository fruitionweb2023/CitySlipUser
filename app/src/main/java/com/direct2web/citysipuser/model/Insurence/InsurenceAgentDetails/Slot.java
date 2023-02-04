package com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails;

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