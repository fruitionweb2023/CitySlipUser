package com.direct2web.citysipuser.model.DoctorModels.HelpAndFaqs.leval2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDoctorHelpSecond {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("level_2_list")
@Expose
private List<Level2> level2List = null;

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

public List<Level2> getLevel2List() {
return level2List;
}

public void setLevel2List(List<Level2> level2List) {
this.level2List = level2List;
}

}