package com.direct2web.citysipuser.model.Insurence.HelpAndFaqs.Level3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseInsurenceHelpThird {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("level_3_list")
@Expose
private List<Level3> level3List = null;

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

public List<Level3> getLevel3List() {
return level3List;
}

public void setLevel3List(List<Level3> level3List) {
this.level3List = level3List;
}

}