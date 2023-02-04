package com.direct2web.citysipuser.model.RestaurentModels.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCategoryList {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("category_list")
@Expose
private List<Category> categoryList = null;

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

public List<Category> getCategoryList() {
return categoryList;
}

public void setCategoryList(List<Category> categoryList) {
this.categoryList = categoryList;
}

}