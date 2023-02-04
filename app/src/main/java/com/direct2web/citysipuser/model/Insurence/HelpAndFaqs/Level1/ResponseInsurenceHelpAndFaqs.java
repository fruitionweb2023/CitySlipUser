package com.direct2web.citysipuser.model.Insurence.HelpAndFaqs.Level1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseInsurenceHelpAndFaqs {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("level_1_list")
@Expose
private List<Level1> level1List = null;

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

public List<Level1> getLevel1List() {
return level1List;
}

public void setLevel1List(List<Level1> level1List) {
this.level1List = level1List;
}

}