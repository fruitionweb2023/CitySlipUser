
package com.direct2web.citysipuser.model.RestaurentModels.BusignessImagesAndVideos;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BusinessImageAndVideo {

    @SerializedName("error")
    private Boolean mError;
    @SerializedName("image")
    private List<Image> mImage;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("video")
    private List<Video> mVideo;

    public Boolean getError() {
        return mError;
    }

    public void setError(Boolean error) {
        mError = error;
    }

    public List<Image> getImage() {
        return mImage;
    }

    public void setImage(List<Image> image) {
        mImage = image;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<Video> getVideo() {
        return mVideo;
    }

    public void setVideo(List<Video> video) {
        mVideo = video;
    }

}
