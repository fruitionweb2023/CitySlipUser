package com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MenuList {

    @SerializedName("dish_type_title")
    private String dishTypeTitle;

    @SerializedName("menu_item")
    private List<MenuItem> menuItem;

    @SerializedName("id")
    private String id;

    public void setDishTypeTitle(String dishTypeTitle){
        this.dishTypeTitle = dishTypeTitle;
    }

    public String getDishTypeTitle(){
        return dishTypeTitle;
    }

    public void setMenuItem(List<MenuItem> menuItem){
        this.menuItem = menuItem;
    }

    public List<MenuItem> getMenuItem(){
        return menuItem;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
}