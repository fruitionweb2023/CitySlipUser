
package com.direct2web.citysipuser.model.DoctorModels;

import com.google.gson.annotations.SerializedName;

public class SliderImage {

    @SerializedName("id")
    private String mId;
    @SerializedName("photo")
    private String mPhoto;

    public SliderImage(String drawable) {
        this.mPhoto = drawable;
    }

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
