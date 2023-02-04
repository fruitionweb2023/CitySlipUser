package com.direct2web.citysipuser.activities.Insurance;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHelpLevel3Adapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceHelpLevel3Adapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorCustomerCareNumberBinding;
import com.direct2web.citysipuser.databinding.ActivityInsurenceCustomerCareNumberBinding;
import com.direct2web.citysipuser.model.DoctorModels.HelpAndFaqs.Leval3.ResponseDoctorHelpThird;
import com.direct2web.citysipuser.model.Insurence.HelpAndFaqs.Level3.Level3;
import com.direct2web.citysipuser.model.Insurence.HelpAndFaqs.Level3.ResponseInsurenceHelpThird;
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

public class InsurenceCustomerCareNumberActivity extends AppCompatActivity {

    ActivityInsurenceCustomerCareNumberBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    String id = "";
    List<Level3> helpList3 = new ArrayList<>();
    InsurenceHelpLevel3Adapter helpLevel3Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurence_customer_care_number);

        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        id = getIntent().getStringExtra("id1");

        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_2C3564));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        getCustomerCareContect(id);
    }

    private void getCustomerCareContect(String level1Id) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseInsurenceHelpThird> call = api.getInsurenceHelpThirdList("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,level1Id);

        call.enqueue(new Callback<ResponseInsurenceHelpThird>() {
            @Override
            public void onResponse(Call<ResponseInsurenceHelpThird> call, Response<ResponseInsurenceHelpThird> response) {

                Log.e("responseCustomerCare", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(InsurenceCustomerCareNumberActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        helpList3 = response.body().getLevel3List();
                        helpLevel3Adapter = new InsurenceHelpLevel3Adapter(helpList3, InsurenceCustomerCareNumberActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InsurenceCustomerCareNumberActivity.this);
                        binding.rclrCustomerCare.setLayoutManager(linearLayoutManager);
                        binding.rclrCustomerCare.setAdapter(helpLevel3Adapter);

                    }

                } else {

                    Toast.makeText(InsurenceCustomerCareNumberActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseInsurenceHelpThird> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}