package com.direct2web.citysipuser.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.direct2web.citysipuser.activities.CommonActivies.CategoryActivity;


public class SessionManager {
    SharedPreferences pre;
    SharedPreferences.Editor edit;
    private static String key_Name="DA";
    int Moad=0;
    Context context;
    private final String userUrl="Pic_URL";
    private final String username="Username";
    private final String storename="Storename";
    private final String userId="ID";
    public final String isLogin="IsLogin";
    public final String businessType="Type";
    private final String usercontact = "usercontact";
    private final String userlocation="userlocation";
    private final String gender="gender";
    private  final String Token="token";
    private final String latitude = "latitude";
    private final String longitude = "longitude";
    private final String businessId = "businessId";

    public String getToken() {
        return Token;
    }
    public void setToken(String token){
        edit.putString(Token,token);
        edit.commit();
    }

    public SessionManager(Context context) {
        this.context = context;
        pre=context.getSharedPreferences(key_Name,0);
        edit=pre.edit();
    }

    public void setLogin(Boolean b){
        edit.putBoolean(isLogin,b);
        edit.commit();
    }

    public boolean checkLogin(){
        return pre.getBoolean(isLogin,false);
    }

    public void setUserId(String s1){
        edit.putString(userId,s1);
        edit.commit();
    }

    public void setBusinessId(String s1){
        edit.putString(businessId,s1);
        edit.commit();
    }
    public String getBusinessId(){
        return pre.getString(businessId,"");
    }

    public String getUserId(){
        return pre.getString(userId,"");
    }


    public void setUserName(String s1){
        edit.putString(username,s1);
        edit.commit();
    }

    public String getUsername(){
        return pre.getString(username,"");
    }

    public void setStoreName(String s1){
        edit.putString(storename,s1);
        edit.commit();
    }

    public String getStorename(){
        return pre.getString(storename,"");
    }

    public void setUserContact(String s1){
        edit.putString(usercontact,s1);
        edit.commit();
    }

    public String getUserContact(){
        return pre.getString(usercontact,"");
    }

    public void setUserGender(String s1){
        edit.putString(gender,s1);
        edit.commit();
    }

    public String getUserGender(){
        return pre.getString(gender,"");
    }

    public void setUserUrl(String s1){
        edit.putString(userUrl,s1);
        edit.commit();
    }

    public String getUserUrl(){
        return pre.getString(userUrl,"");
    }

    public void LogOut() {
        edit.clear();
        edit.commit();

        Intent intent = new Intent(context, CategoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ((Activity) context).finish();
        context.startActivity(intent);
    }

    public void setBusinesstype(String strType) {
        edit.putString(businessType,strType);
        edit.commit();
    }

    public String getBusinessType(){
        return pre.getString(businessType,"");
    }

    public void setLatLong(String latLng) {
        edit.putString(userlocation, latLng);
        edit.commit();
    }

    public String getLatlong(){
        return pre.getString(userlocation,"");
    }

    public void setLat(String latLng) {
        edit.putString(latitude, latLng);
        edit.commit();
    }

    public String getLat() {
        return pre.getString(latitude, "");
    }
    public void setLong(String latLng) {
        edit.putString(longitude, latLng);
        edit.commit();
    }

    public String getLong() {
        return pre.getString(longitude, "");
    }

}
