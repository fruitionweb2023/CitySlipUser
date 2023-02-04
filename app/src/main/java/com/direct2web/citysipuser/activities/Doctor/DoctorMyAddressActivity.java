package com.direct2web.citysipuser.activities.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Restaurent.MyAddressActivity;
import com.direct2web.citysipuser.adapters.DoctorAdapter.Account.DoctorAddressListAdapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.Account.AddressListAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorMyAddressBinding;
import com.direct2web.citysipuser.model.DoctorModels.Account.Address;
import com.direct2web.citysipuser.model.DoctorModels.Account.ResponseDoctorAddressList;
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

public class DoctorMyAddressActivity extends AppCompatActivity {

    ActivityDoctorMyAddressBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    List<Address> addressListItems = new ArrayList<>();
    DoctorAddressListAdapter addressListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_my_address);

        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);


        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_0059C8));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        getUserAddress(sessionManager.getUserId());
    }


    private void getUserAddress(String userId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseDoctorAddressList> call = api.getDoctorUserAddress("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, userId);

        call.enqueue(new Callback<ResponseDoctorAddressList>() {
            @Override
            public void onResponse(Call<ResponseDoctorAddressList> call, Response<ResponseDoctorAddressList> response) {

                Log.e("responseReview", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorMyAddressActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        addressListItems = response.body().getAddressList();
                        addressListAdapter = new DoctorAddressListAdapter(addressListItems, DoctorMyAddressActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorMyAddressActivity.this);
                        binding.rclrAddressList.setLayoutManager(linearLayoutManager);
                        binding.rclrAddressList.setAdapter(addressListAdapter);

                    }

                } else {

                    Toast.makeText(DoctorMyAddressActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorAddressList> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}