package com.direct2web.citysipuser.activities.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Restaurent.ApplyCouponsActivity;
import com.direct2web.citysipuser.activities.Restaurent.CartActivity;
import com.direct2web.citysipuser.adapters.DoctorAdapter.Account.DoctorAddressListAdapter;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHospitalList.DoctorOfferListAdapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.Cart.OfferListAdapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.Cart.ResponseDoctorApplyPromocode;
import com.direct2web.citysipuser.databinding.ActivityDoctorApplyCouponsBinding;
import com.direct2web.citysipuser.databinding.RawDoctorCouponListApplyBinding;
import com.direct2web.citysipuser.model.DoctorModels.Account.ResponseDoctorAddressList;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.Offer;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseDoctorOfferList;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.OfferListItem;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseOfferList;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorApplyCouponsActivity extends AppCompatActivity implements DoctorOfferListAdapter.OnApplyButtonClicked {

    ActivityDoctorApplyCouponsBinding binding;
    SessionManager sessionManager;
    ProgressDialog pd;
    List<Offer> offerItem = new ArrayList<>();
    String c_id = "";
    String a = "",discount = "";
    BottomButtonClickListner bottomButtonClickListner;
    DoctorOfferListAdapter doctorOfferListAdapter;
    SharedPreferences shrPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_apply_coupons);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgOrder.setColorFilter(getResources().getColor(R.color.clr_0059C8));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        shrPref = getSharedPreferences("offerDiscount",Context.MODE_PRIVATE);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DoctorCartData", Context.MODE_PRIVATE);
         a = sharedPreferences.getString("amount","");

        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("offerDiscount", Context.MODE_PRIVATE);
        discount = sharedPreferences1.getString("discount","");

        c_id = getIntent().getStringExtra("c_id");
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();



        getRestaurentDetails(c_id);

    }

    private void getRestaurentDetails(String productList) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseDoctorOfferList> call = api.getDoctorOfferList("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,productList);

        call.enqueue(new Callback<ResponseDoctorOfferList>() {
            @Override
            public void onResponse(Call<ResponseDoctorOfferList> call, Response<ResponseDoctorOfferList> response) {

                Log.e("responseOfferList", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorApplyCouponsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        offerItem = response.body().getOfferList();

                        doctorOfferListAdapter = new DoctorOfferListAdapter(offerItem, DoctorApplyCouponsActivity.this,DoctorApplyCouponsActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorApplyCouponsActivity.this);
                        binding.rvCouponsListItem.setLayoutManager(linearLayoutManager);
                        binding.rvCouponsListItem.setAdapter(doctorOfferListAdapter);
                    }

                } else {

                    Toast.makeText(DoctorApplyCouponsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorOfferList> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onApplyClicked(int postion) {

        Offer offerItemData = offerItem.get(postion);

        String couponCode = offerItemData.getCoupnCode();

        getUserAddress(sessionManager.getUserId(),a,discount);
        Intent intent = new Intent(DoctorApplyCouponsActivity.this, DoctorCartActivity.class);
        intent.putExtra("CouponCode",couponCode);
        startActivity(intent);
    }


    private void getUserAddress(String userId,String totleAmount,String discountValue) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseDoctorApplyPromocode> call = api.sendPromocode("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, userId,totleAmount,discountValue);

        call.enqueue(new Callback<ResponseDoctorApplyPromocode>() {
            @Override
            public void onResponse(Call<ResponseDoctorApplyPromocode> call, Response<ResponseDoctorApplyPromocode> response) {

                Log.e("respponseDiscount", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorApplyCouponsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {


                        SharedPreferences.Editor editor = shrPref.edit();
                        editor.putString("discount",response.body().getDiscount());
                        editor.commit();
                        Toast.makeText(DoctorApplyCouponsActivity.this, "discount : " + discount, Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(DoctorApplyCouponsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorApplyPromocode> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}