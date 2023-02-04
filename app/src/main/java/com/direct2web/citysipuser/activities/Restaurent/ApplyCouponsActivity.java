package com.direct2web.citysipuser.activities.Restaurent;

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
import com.direct2web.citysipuser.adapters.RestaurentAdapter.Cart.OfferListAdapter;
import com.direct2web.citysipuser.databinding.ActivityApplyCouponsBinding;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.OfferListItem;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseOfferList;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseRestaurentApplyPromocode;
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

public class ApplyCouponsActivity extends AppCompatActivity implements OfferListAdapter.OnApplyButtonClicked {

    ActivityApplyCouponsBinding binding;
    SessionManager sessionManager;
    ProgressDialog pd;
    List<OfferListItem> offerItem = new ArrayList<>();
    OfferListAdapter offerListAdapter;
    String c_id = "", a = "",discount = "", itemTotle;
    BottomButtonClickListner bottomButtonClickListner;
    SharedPreferences sharPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_apply_coupons);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);
        itemTotle = getIntent().getStringExtra("item_totle");

        binding.bottomnavigation.bbImgOrder.setColorFilter(getResources().getColor(R.color.clr_f54748));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        sharPref = getSharedPreferences("offerDiscount", Context.MODE_PRIVATE);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DoctorCartData", Context.MODE_PRIVATE);
        a = sharedPreferences.getString("amount","");

        SharedPreferences Preferences1 = getApplicationContext().getSharedPreferences("offerDiscount", Context.MODE_PRIVATE);
        discount = Preferences1.getString("discount","");

        c_id = getIntent().getStringExtra("c_id");
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        getRestaurentDetails(c_id);

    }

    private void getRestaurentDetails(String productList) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseOfferList> call = api.getOfferList("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,productList);

        call.enqueue(new Callback<ResponseOfferList>() {
            @Override
            public void onResponse(Call<ResponseOfferList> call, Response<ResponseOfferList> response) {

                Log.e("responseOrder", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().isError()) {

                        Toast.makeText(ApplyCouponsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {


                        offerItem = response.body().getOfferList();

                        offerListAdapter = new OfferListAdapter(offerItem, ApplyCouponsActivity.this,ApplyCouponsActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ApplyCouponsActivity.this);
                        binding.rvCouponsListItem.setLayoutManager(linearLayoutManager);
                        binding.rvCouponsListItem.setAdapter(offerListAdapter);

                    }

                } else {

                    Toast.makeText(ApplyCouponsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseOfferList> call, Throwable t) {

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

        OfferListItem offerItemData = offerItem.get(postion);

        String couponCode = offerItemData.getCoupnCode();
        getUserAddress(sessionManager.getUserId(),itemTotle,couponCode);

    }

    private void getUserAddress(String userId,String totleAmount,String discountValue) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseRestaurentApplyPromocode> call = api.sendRestaurentPromocode("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, userId,totleAmount,discountValue);

        call.enqueue(new Callback<ResponseRestaurentApplyPromocode>() {
            @Override
            public void onResponse(Call<ResponseRestaurentApplyPromocode> call, Response<ResponseRestaurentApplyPromocode> response) {

                Log.e("respponseDiscount", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(ApplyCouponsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(ApplyCouponsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ApplyCouponsActivity.this,CartActivity.class);
                        intent.putExtra("CouponCode",response.body().getPromo_code());
                        intent.putExtra("discount",response.body().getDiscount());
                        startActivity(intent);
                        /*SharedPreferences.Editor editor = sharPref.edit();
                        editor.putString("discount",response.body().getDiscount());
                        editor.commit();*/

                    }

                } else {

                    Toast.makeText(ApplyCouponsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseRestaurentApplyPromocode> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}