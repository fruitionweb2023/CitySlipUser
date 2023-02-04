package com.direct2web.citysipuser.activities.Insurance;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.DoctorAdapter.Account.DoctorAddressListAdapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceAddressListAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorMyAddressBinding;
import com.direct2web.citysipuser.databinding.ActivityInsurenceMyAddressBinding;
import com.direct2web.citysipuser.model.DoctorModels.Account.ResponseDoctorAddressList;
import com.direct2web.citysipuser.model.Insurence.Account.Address;
import com.direct2web.citysipuser.model.Insurence.Account.ResponseInsurenceAddressList;
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

public class InsurenceMyAddressActivity extends AppCompatActivity {

    ActivityInsurenceMyAddressBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    List<Address> addressListItems = new ArrayList<>();
    InsurenceAddressListAdapter addressListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurence_my_address);

        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);


        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_2C3564));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        getUserAddress(sessionManager.getUserId());
    }


    private void getUserAddress(String userId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseInsurenceAddressList> call = api.getInsurenceUserAddress("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, userId);

        call.enqueue(new Callback<ResponseInsurenceAddressList>() {
            @Override
            public void onResponse(Call<ResponseInsurenceAddressList> call, Response<ResponseInsurenceAddressList> response) {

                Log.e("responseReview", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(InsurenceMyAddressActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        addressListItems = response.body().getAddressList();
                        addressListAdapter = new InsurenceAddressListAdapter(addressListItems, InsurenceMyAddressActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InsurenceMyAddressActivity.this);
                        binding.rclrAddressList.setLayoutManager(linearLayoutManager);
                        binding.rclrAddressList.setAdapter(addressListAdapter);

                    }

                } else {

                    Toast.makeText(InsurenceMyAddressActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseInsurenceAddressList> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}