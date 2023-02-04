
package com.direct2web.citysipuser.model.RestaurentModels.ReadMoreDetails;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("id")
    private String mId;
    @SerializedName("photo")
    private String mPhoto;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

}
