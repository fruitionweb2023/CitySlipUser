package com.direct2web.citysipuser.model.Insurence.HelpAndFaqs.Level3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Level3 {

@SerializedName("id")
@Expose
private String id;
@SerializedName("title")
@Expose
private String title;
@SerializedName("description")
@Expose
private String description;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

}