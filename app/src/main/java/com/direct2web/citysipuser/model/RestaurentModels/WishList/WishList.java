
package com.direct2web.citysipuser.model.RestaurentModels.WishList;

import com.google.gson.annotations.SerializedName;

public class WishList {

    @SerializedName("error")
    private Boolean mError;
    @SerializedName("message")
    private String mMessage;

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

}
