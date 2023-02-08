package com.direct2web.citysipuser.activities.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.DoctorAdapter.Account.DoctorAllOfferListAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorOffersBinding;
import com.direct2web.citysipuser.model.DoctorModels.Account.Offer;
import com.direct2web.citysipuser.model.DoctorModels.Account.ResponseDoctorAllOfferList;
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

public class DoctorOffersActivity extends AppCompatActivity {

    ActivityDoctorOffersBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    List<Offer> offerItem = new ArrayList<>();
    DoctorAllOfferListAdapter offerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_offers);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_0059C8));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        getOfferList();
    }

    private void getOfferList() {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseDoctorAllOfferList> call = api.getDoctorOffers("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key);

        call.enqueue(new Callback<ResponseDoctorAllOfferList>() {
            @Override
            public void onResponse(Call<ResponseDoctorAllOfferList> call, Response<ResponseDoctorAllOfferList> response) {

                Log.e("offerList", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {
                        if(response.body().getEmpty()) {
                            binding.llError.setVisibility(View.VISIBLE);
                            binding.llMain.setVisibility(View.GONE);
                            Glide.with(DoctorOffersActivity.this)
                                    .load(Api.imageUrl + response.body().getErrorImage())
                                    .fitCenter()
                                    .into(binding.imgError);
                        }
                        Toast.makeText(DoctorOffersActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        binding.llError.setVisibility(View.GONE);
                        binding.llMain.setVisibility(View.VISIBLE);
                        offerItem = response.body().getOfferList();

                        offerListAdapter = new DoctorAllOfferListAdapter(offerItem, DoctorOffersActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorOffersActivity.this);
                        binding.rvCouponsListItem.setLayoutManager(linearLayoutManager);
                        binding.rvCouponsListItem.setAdapter(offerListAdapter);
                    }

                } else {

                    Toast.makeText(DoctorOffersActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorAllOfferList> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}