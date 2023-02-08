package com.direct2web.citysipuser.model.DoctorModels.Account;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDoctorFavouriteHospital {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
    @SerializedName("no_data")
    @Expose
    private Boolean isEmpty;

    @SerializedName("no_data_image")
    @Expose
    private String errorImage;
@SerializedName("wish_list_business")
@Expose
private List<WishListBusiness> wishListBusiness = null;

    public Boolean getEmpty() {
        return isEmpty;
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }

    public String getErrorImage() {
        return errorImage;
    }

    public void setErrorImage(String errorImage) {
        this.errorImage = errorImage;
    }

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

public List<WishListBusiness> getWishListBusiness() {
return wishListBusiness;
}

public void setWishListBusiness(List<WishListBusiness> wishListBusiness) {
this.wishListBusiness = wishListBusiness;
}

}