package com.direct2web.citysipuser.model.LawyerModels.HelpAndFaqs.Level2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Level2 {

@SerializedName("id")
@Expose
private String id;
@SerializedName("level_2_title")
@Expose
private String level2Title;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getLevel2Title() {
return level2Title;
}

public void setLevel2Title(String level2Title) {
this.level2Title = level2Title;
}

}