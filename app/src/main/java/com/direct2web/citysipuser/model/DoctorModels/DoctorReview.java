
package com.direct2web.citysipuser.model.DoctorModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorReview {

    @SerializedName("error")
    private Boolean mError;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("overall_ratings")
    private String mOverallRatings;
    @SerializedName("reviews")
    private List<Review> mReviews;
    @SerializedName("total_rating")
    private String mTotalRating;

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

    public String getOverallRatings() {
        return mOverallRatings;
    }

    public void setOverallRatings(String overallRatings) {
        mOverallRatings = overallRatings;
    }

    public List<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }

    public String getTotalRating() {
        return mTotalRating;
    }

    public void setTotalRating(String totalRating) {
        mTotalRating = totalRating;
    }

}
