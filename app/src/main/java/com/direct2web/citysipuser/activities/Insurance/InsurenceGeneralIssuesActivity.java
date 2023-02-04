package com.direct2web.citysipuser.activities.Insurance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorCustomerCareNumberActivity;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHelpLevel2Adapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceHelpLevel2Adapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorGeneralIssuesBinding;
import com.direct2web.citysipuser.databinding.ActivityInsurenceGeneralIssuesBinding;
import com.direct2web.citysipuser.model.DoctorModels.HelpAndFaqs.leval2.ResponseDoctorHelpSecond;
import com.direct2web.citysipuser.model.Insurence.HelpAndFaqs.Level2.Level2;
import com.direct2web.citysipuser.model.Insurence.HelpAndFaqs.Level2.ResponseInsurenceHelpSecond;
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

public class InsurenceGeneralIssuesActivity extends AppCompatActivity implements InsurenceHelpLevel2Adapter.OnItemClickListner {


    ActivityInsurenceGeneralIssuesBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    String id = "", title = "";
    String helpId = "", helpTitle = "";
    List<Level2> helpList2 = new ArrayList<>();
    InsurenceHelpLevel2Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurence_general_issues);

        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");


        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_2C3564));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        binding.txtDashboard.setText(title);
        getUserAddress(id);
    }

    private void getUserAddress(String level1Id) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseInsurenceHelpSecond> call = api.getInsurenceHelpsecondList("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,level1Id);

        call.enqueue(new Callback<ResponseInsurenceHelpSecond>() {
            @Override
            public void onResponse(Call<ResponseInsurenceHelpSecond> call, Response<ResponseInsurenceHelpSecond> response) {

                Log.e("responseGenerralIssuew", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(InsurenceGeneralIssuesActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        helpList2 = response.body().getLevel2List();
                        adapter = new InsurenceHelpLevel2Adapter(helpList2, InsurenceGeneralIssuesActivity.this, InsurenceGeneralIssuesActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InsurenceGeneralIssuesActivity.this);
                        binding.rclrGeneralIssue.setLayoutManager(linearLayoutManager);
                        binding.rclrGeneralIssue.setAdapter(adapter);

                    }

                } else {

                    Toast.makeText(InsurenceGeneralIssuesActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseInsurenceHelpSecond> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onClicked(int postion) {
        Intent intent = new Intent(InsurenceGeneralIssuesActivity.this, DoctorCustomerCareNumberActivity.class);
        helpId =  helpList2.get(postion).getId();
        helpTitle = helpList2.get(postion).getLevel2Title();
        intent.putExtra("id1",helpId);
        startActivity(intent);
    }
}