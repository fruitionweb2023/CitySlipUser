package com.direct2web.citysipuser.model.RestaurentModels.Cart;

import com.google.gson.annotations.SerializedName;

public class CartItem{

    @SerializedName("price")
    private String price;

    @SerializedName("qty")
    private String qty;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("category")
    private String category;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public void setQty(String qty){
        this.qty = qty;
    }

    public String getQty(){
        return qty;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}