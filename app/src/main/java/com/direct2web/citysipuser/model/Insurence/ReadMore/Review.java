
package com.direct2web.citysipuser.model.Insurence.ReadMore;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("comment")
    private String mComment;
    @SerializedName("name")
    private String mName;
    @SerializedName("rating")
    private String mRating;
    @SerializedName("submit_date")
    private String mSubmitDate;

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public String getSubmitDate() {
        return mSubmitDate;
    }

    public void setSubmitDate(String submitDate) {
        mSubmitDate = submitDate;
    }

}
