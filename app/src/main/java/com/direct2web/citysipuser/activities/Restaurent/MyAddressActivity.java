package com.direct2web.citysipuser.activities.Restaurent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.Account.AddressListAdapter;
import com.direct2web.citysipuser.databinding.ActivityMyAddressBinding;
import com.direct2web.citysipuser.model.RestaurentModels.Account.Address;
import com.direct2web.citysipuser.model.RestaurentModels.Account.ResponseAddressList;
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

public class MyAddressActivity extends AppCompatActivity {

    ActivityMyAddressBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    List<Address> addressListItems = new ArrayList<>();
    AddressListAdapter addressListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_address);

        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);


        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_f54748));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        getUserAddress(sessionManager.getUserId());
    }

    private void getUserAddress(String userId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseAddressList> call = api.getUserAddress("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, userId);

        call.enqueue(new Callback<ResponseAddressList>() {
            @Override
            public void onResponse(Call<ResponseAddressList> call, Response<ResponseAddressList> response) {

                Log.e("responseReview", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(MyAddressActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        addressListItems = response.body().getAddressList();
                        addressListAdapter = new AddressListAdapter(addressListItems, MyAddressActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyAddressActivity.this);
                        binding.rclrAddressList.setLayoutManager(linearLayoutManager);
                        binding.rclrAddressList.setAdapter(addressListAdapter);

                    }

                } else {

                    Toast.makeText(MyAddressActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseAddressList> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}