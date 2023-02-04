package com.direct2web.citysipuser.activities.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Restaurent.CustomerCareNumberActivity;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHelpLevel3Adapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.HelpAndFAQS.HelpLevel3Adapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorCustomerCareNumberBinding;
import com.direct2web.citysipuser.model.DoctorModels.HelpAndFaqs.Leval3.Level3;
import com.direct2web.citysipuser.model.DoctorModels.HelpAndFaqs.Leval3.ResponseDoctorHelpThird;

import com.direct2web.citysipuser.model.RestaurentModels.Help.level3.ResponseHelpThird;
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

public class DoctorCustomerCareNumberActivity extends AppCompatActivity {

    ActivityDoctorCustomerCareNumberBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    String id = "";
    List<Level3> helpList3 = new ArrayList<>();
    DoctorHelpLevel3Adapter helpLevel3Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_customer_care_number);

        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        id = getIntent().getStringExtra("id1");

        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_0059C8));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        getCustomerCareContect(id);
    }

    private void getCustomerCareContect(String level1Id) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseDoctorHelpThird> call = api.getDoctorHelpThirdList("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,level1Id);

        call.enqueue(new Callback<ResponseDoctorHelpThird>() {
            @Override
            public void onResponse(Call<ResponseDoctorHelpThird> call, Response<ResponseDoctorHelpThird> response) {

                Log.e("responseCustomerCare", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorCustomerCareNumberActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        helpList3 = response.body().getLevel3List();
                        helpLevel3Adapter = new DoctorHelpLevel3Adapter(helpList3, DoctorCustomerCareNumberActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorCustomerCareNumberActivity.this);
                        binding.rclrCustomerCare.setLayoutManager(linearLayoutManager);
                        binding.rclrCustomerCare.setAdapter(helpLevel3Adapter);

                    }

                } else {

                    Toast.makeText(DoctorCustomerCareNumberActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorHelpThird> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}