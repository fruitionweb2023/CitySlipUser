package com.direct2web.citysipuser.model.LawyerModels.LawyerDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLawyerList {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("recommended_list")
@Expose
private List<Recommended> recommendedList = null;
@SerializedName("near_by_list")
@Expose
private List<NearBy> nearByList = null;

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

public List<Recommended> getRecommendedList() {
return recommendedList;
}

public void setRecommendedList(List<Recommended> recommendedList) {
this.recommendedList = recommendedList;
}

public List<NearBy> getNearByList() {
return nearByList;
}

public void setNearByList(List<NearBy> nearByList) {
this.nearByList = nearByList;
}

}