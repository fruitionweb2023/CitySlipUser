package com.direct2web.citysipuser.activities.Insurance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceAgentListadapter;
import com.direct2web.citysipuser.databinding.ActivityInsurenceAgentsDetailsBinding;
import com.direct2web.citysipuser.model.Insurence.InsurenceServiceWiseAgent.Agent;
import com.direct2web.citysipuser.model.Insurence.InsurenceServiceWiseAgent.ResponseInsurenceServiceWiseAgent;
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

public class InsurenceAgentsDetails extends AppCompatActivity implements InsurenceAgentListadapter.OnItemClickListnerNearBy {

    ActivityInsurenceAgentsDetailsBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    ProgressDialog pd;
    List<Agent> agentList = new ArrayList<>();
    InsurenceAgentListadapter insurenceAgentListadapter;
    String serviceId = "",serviceName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurence_agents_details);
        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_2C3564));
        binding.bottomnavigation.bbTxtHome.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        serviceId = getIntent().getStringExtra("service_id");
        serviceName = getIntent().getStringExtra("service_name");
        binding.txtTitle.setText(serviceName);

        getRestaurentDetails(serviceId);

    }


    private void getRestaurentDetails(String businessId) {

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseInsurenceServiceWiseAgent> call = api.getInsurenceServiceWiseAgent("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,businessId);

        call.enqueue(new Callback<ResponseInsurenceServiceWiseAgent>() {
            @Override
            public void onResponse(Call<ResponseInsurenceServiceWiseAgent> call, Response<ResponseInsurenceServiceWiseAgent> response) {

                Log.e("responseServices", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(InsurenceAgentsDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        agentList = response.body().getAgentList();
                        insurenceAgentListadapter = new InsurenceAgentListadapter(agentList, InsurenceAgentsDetails.this, InsurenceAgentsDetails.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InsurenceAgentsDetails.this);
                        binding.rclrAddressList.setLayoutManager(linearLayoutManager);
                        binding.rclrAddressList.setAdapter(insurenceAgentListadapter);

                    }

                } else {

                    Toast.makeText(InsurenceAgentsDetails.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseInsurenceServiceWiseAgent> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onNearByItemClicked(int postion) {
        Intent i = new Intent(InsurenceAgentsDetails.this, InsurenceDetailsActivity.class);
        i.putExtra("agent_id", agentList.get(postion).getId());
        startActivity(i);
    }
}