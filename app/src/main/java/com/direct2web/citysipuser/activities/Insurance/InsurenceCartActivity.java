package com.direct2web.citysipuser.activities.Insurance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorApplyCouponsActivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorPaymentMethodactivity;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorCartAdapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceAddServiceAdapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceAllListAdapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceCartAdapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceListAdapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceNearbyListadapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorYourAppointmentsBinding;
import com.direct2web.citysipuser.databinding.ActivityInsurenceCartBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.Cart;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseDoctorCartItem;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.ResponseInsurenceAgentDetails;
import com.direct2web.citysipuser.model.Insurence.InsurenceList.ResponseInsurenceList;
import com.direct2web.citysipuser.model.Insurence.InsurenceList.Service;
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

public class InsurenceCartActivity extends AppCompatActivity implements InsurenceAllListAdapter.OnItemClickListner {

    ActivityInsurenceCartBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    ProgressDialog pd;
    List<Service> recommendedList = new ArrayList<>();
    InsurenceAllListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurence_cart);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgOrder.setColorFilter(getResources().getColor(R.color.clr_2C3564));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        getRestaurentDetails(sessionManager.getBusinessType());

    }
    private void getRestaurentDetails(String businessId) {

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseInsurenceList> call = api.getInsurenceAgentList("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,businessId,"","","","");

        call.enqueue(new Callback<ResponseInsurenceList>() {
            @Override
            public void onResponse(Call<ResponseInsurenceList> call, Response<ResponseInsurenceList> response) {

                Log.e("responseInsurenceList", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(InsurenceCartActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        recommendedList = response.body().getServiceList();
                        adapter = new InsurenceAllListAdapter(recommendedList, InsurenceCartActivity.this, InsurenceCartActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InsurenceCartActivity.this);
                        binding.rclrNearby.setLayoutManager(linearLayoutManager);
                        binding.rclrNearby.setAdapter(adapter);


                    }

                } else {

                    Toast.makeText(InsurenceCartActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseInsurenceList> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onItemClicked(int postion) {

        Intent i = new Intent(InsurenceCartActivity.this, InsurenceAgentsDetails.class);
        i.putExtra("service_id", recommendedList.get(postion).getId());
        startActivity(i);
    }
}