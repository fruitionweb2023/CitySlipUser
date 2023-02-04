
package com.direct2web.citysipuser.model.LawyerModels.LawyerDetails;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("id")
    private String mId;
    @SerializedName("video")
    private String mVideo;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getVideo() {
        return mVideo;
    }

    public void setVideo(String video) {
        mVideo = video;
    }

}
