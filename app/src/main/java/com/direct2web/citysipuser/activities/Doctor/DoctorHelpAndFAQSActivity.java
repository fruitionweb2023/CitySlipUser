package com.direct2web.citysipuser.activities.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHelpAdapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.HelpAndFAQS.HelpAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorHelpAndFaqsactivityBinding;
import com.direct2web.citysipuser.model.DoctorModels.HelpAndFaqs.Leval1.Level1;
import com.direct2web.citysipuser.model.DoctorModels.HelpAndFaqs.Leval1.ResponseDoctorHelpAndFaqs;
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

public class DoctorHelpAndFAQSActivity extends AppCompatActivity implements HelpAdapter.OnItemClickListner, DoctorHelpAdapter.OnItemClickListner {

    ActivityDoctorHelpAndFaqsactivityBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    List<Level1> helpList = new ArrayList<>();
    DoctorHelpAdapter helpAdapter;
    String helpId = "",helpTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_help_and_faqsactivity);

        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);


        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_0059C8));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        getUserAddress();
    }

    private void getUserAddress() {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseDoctorHelpAndFaqs> call = api.getDoctorHelp("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key);

        call.enqueue(new Callback<ResponseDoctorHelpAndFaqs>() {
            @Override
            public void onResponse(Call<ResponseDoctorHelpAndFaqs> call, Response<ResponseDoctorHelpAndFaqs> response) {

                Log.e("responseHelpAndFaqs", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorHelpAndFAQSActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        helpList = response.body().getLevel1List();
                        helpAdapter = new DoctorHelpAdapter(helpList, DoctorHelpAndFAQSActivity.this,DoctorHelpAndFAQSActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorHelpAndFAQSActivity.this);
                        binding.rclrHelpAndFaqs.setLayoutManager(linearLayoutManager);
                        binding.rclrHelpAndFaqs.setAdapter(helpAdapter);

                    }

                } else {

                    Toast.makeText(DoctorHelpAndFAQSActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorHelpAndFaqs> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onClicked(int postion) {
        Intent intent = new Intent(DoctorHelpAndFAQSActivity.this, DoctorGeneralIssuesActivity.class);
        helpId = helpList.get(postion).getId();
        helpTitle = helpList.get(postion).getTitle();
        intent.putExtra("id",helpId);
        intent.putExtra("title",helpTitle);
        startActivity(intent);
    }
}