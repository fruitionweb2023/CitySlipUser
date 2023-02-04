package com.direct2web.citysipuser.activities.Restaurent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.HelpAndFAQS.HelpAdapter;
import com.direct2web.citysipuser.databinding.ActivityHelpAndFaqsBinding;
import com.direct2web.citysipuser.model.RestaurentModels.Help.level1.Level1;
import com.direct2web.citysipuser.model.RestaurentModels.Help.level1.ResponseHelpMain;
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

public class HelpAndFaqsActivity extends AppCompatActivity implements HelpAdapter.OnItemClickListner {

    ActivityHelpAndFaqsBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    List<Level1> helpList = new ArrayList<>();
    HelpAdapter helpAdapter;
    String helpId = "",helpTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_help_and_faqs);

        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);


        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_f54748));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        /*binding.txtGeneralIssues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HelpAndFaqsActivity.this, GeneralIssuesActivity.class);
                startActivity(intent);
            }
        });*/

        getUserAddress();
    }

    private void getUserAddress() {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseHelpMain> call = api.getHelp("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key);

        call.enqueue(new Callback<ResponseHelpMain>() {
            @Override
            public void onResponse(Call<ResponseHelpMain> call, Response<ResponseHelpMain> response) {

                Log.e("responseHelpAndFaqs", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(HelpAndFaqsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        helpList = response.body().getLevel1List();
                        helpAdapter = new HelpAdapter(helpList, HelpAndFaqsActivity.this,HelpAndFaqsActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HelpAndFaqsActivity.this);
                        binding.rclrHelpAndFaqs.setLayoutManager(linearLayoutManager);
                        binding.rclrHelpAndFaqs.setAdapter(helpAdapter);

                    }

                } else {

                    Toast.makeText(HelpAndFaqsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseHelpMain> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onClicked(int postion) {
        Intent intent = new Intent(HelpAndFaqsActivity.this,GeneralIssuesActivity.class);
        helpId = helpList.get(postion).getId();
        helpTitle = helpList.get(postion).getTitle();
        intent.putExtra("id",helpId);
        intent.putExtra("title",helpTitle);
        startActivity(intent);
    }
}