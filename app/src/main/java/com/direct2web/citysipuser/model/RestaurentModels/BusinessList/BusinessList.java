
package com.direct2web.citysipuser.model.RestaurentModels.BusinessList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BusinessList {

    @SerializedName("error")
    private Boolean mError;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("near_by_list")
    private List<NearByList> mNearByList;
    @SerializedName("recommended_list")
    private List<RecommendedList> mRecommendedList;

    public Boolean getError() {
        return mError;
    }

    public void setError(Boolean error) {
        mError = error;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<NearByList> getNearByList() {
        return mNearByList;
    }

    public void setNearByList(List<NearByList> nearByList) {
        mNearByList = nearByList;
    }

    public List<RecommendedList> getRecommendedList() {
        return mRecommendedList;
    }

    public void setRecommendedList(List<RecommendedList> recommendedList) {
        mRecommendedList = recommendedList;
    }

}
