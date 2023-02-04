package com.direct2web.citysipuser.activities.Restaurent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.Account.AllOfferListAdapter;
import com.direct2web.citysipuser.databinding.ActivityOfferBinding;
import com.direct2web.citysipuser.model.RestaurentModels.Account.Offer;
import com.direct2web.citysipuser.model.RestaurentModels.Account.ResponseRestaurentAllOfferList;
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

public class OfferActivity extends AppCompatActivity {

    ActivityOfferBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    List<Offer> offerItem = new ArrayList<>();
    AllOfferListAdapter offerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offer);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_f54748));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        getOfferList();
    }

    private void getOfferList() {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseRestaurentAllOfferList> call = api.getOffers("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key);

        call.enqueue(new Callback<ResponseRestaurentAllOfferList>() {
            @Override
            public void onResponse(Call<ResponseRestaurentAllOfferList> call, Response<ResponseRestaurentAllOfferList> response) {

                Log.e("offerList", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(OfferActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        offerItem = response.body().getOfferList();

                        offerListAdapter = new AllOfferListAdapter(offerItem, OfferActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OfferActivity.this);
                        binding.rvCouponsListItem.setLayoutManager(linearLayoutManager);
                        binding.rvCouponsListItem.setAdapter(offerListAdapter);
                    }

                } else {

                    Toast.makeText(OfferActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseRestaurentAllOfferList> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}