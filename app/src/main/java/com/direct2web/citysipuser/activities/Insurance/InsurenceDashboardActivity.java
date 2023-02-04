package com.direct2web.citysipuser.activities.Insurance;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorHospitalDetailsActivity;
import com.direct2web.citysipuser.activities.Restaurent.MainActivity;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHospitalList.DoctorHospitalListAdapter;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHospitalList.DoctorHospitalNearbyListadapter;
import com.direct2web.citysipuser.adapters.DoctorAdapter.SliderImageListAdapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceListAdapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceNearbyListadapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorDashboardBinding;
import com.direct2web.citysipuser.databinding.ActivityInsurenceDashboardBinding;
import com.direct2web.citysipuser.databinding.FilterByBottomSheetBinding;
import com.direct2web.citysipuser.databinding.FilterByBottomSheetInsurenceBinding;
import com.direct2web.citysipuser.databinding.SortListBottomsheetBinding;
import com.direct2web.citysipuser.databinding.SortListBottomsheetInsurenceBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalList.NearBy;
import com.direct2web.citysipuser.model.DoctorModels.HospitalList.Recommended;
import com.direct2web.citysipuser.model.DoctorModels.HospitalList.ResponseDoctorHospitalList;
import com.direct2web.citysipuser.model.Insurence.InsurenceList.Agent;
import com.direct2web.citysipuser.model.Insurence.InsurenceList.ResponseInsurenceList;
import com.direct2web.citysipuser.model.Insurence.InsurenceList.Service;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsurenceDashboardActivity extends AppCompatActivity implements InsurenceListAdapter.OnItemClickListner, InsurenceNearbyListadapter.OnItemClickListnerNearBy {

    ActivityInsurenceDashboardBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    ProgressDialog pd;
    InsurenceListAdapter adapter;
    InsurenceNearbyListadapter insurenceNearbyListadapter;
    SliderImageListAdapter sliderImageListAdapter;
    List<Service> recommendedList = new ArrayList<>();
    List<Agent> nearByList = new ArrayList<>();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurence_dashboard);
        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_2C3564));
        binding.bottomnavigation.bbTxtHome.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        getRestaurentDetails(sessionManager.getBusinessType());

        sliderImageListAdapter = new SliderImageListAdapter(this);
        binding.imageContainer.setAdapter(sliderImageListAdapter);

        binding.txtFilterBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(InsurenceDashboardActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(InsurenceDashboardActivity.this);
                FilterByBottomSheetInsurenceBinding binding3 = DataBindingUtil.inflate(layoutInflater, R.layout.filter_by_bottom_sheet_insurence, null, false);
                bottomSheetDialog.setContentView(binding3.getRoot());
                bottomSheetDialog.show();
                binding3.btnGetStarted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.hide();
                    }
                });

            }
        });

        binding.txtSortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(InsurenceDashboardActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(InsurenceDashboardActivity.this);
                SortListBottomsheetInsurenceBinding binding2 = DataBindingUtil.inflate(layoutInflater, R.layout.sort_list_bottomsheet_insurence, null, false);
                bottomSheetDialog.setContentView(binding2.getRoot());
                bottomSheetDialog.show();
                binding2.btnGetStarted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.hide();
                    }
                });
            }
        });

        binding.txtSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InsurenceDashboardActivity.this, InsurenceCartActivity.class);
                startActivity(i);
            }
        });
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

                        Toast.makeText(InsurenceDashboardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        recommendedList = response.body().getServiceList();
                        adapter = new InsurenceListAdapter(recommendedList, InsurenceDashboardActivity.this, InsurenceDashboardActivity.this);
                        binding.rclrRecommanded.setAdapter(adapter);

                        nearByList = response.body().getAgentList();
                        insurenceNearbyListadapter = new InsurenceNearbyListadapter(nearByList, InsurenceDashboardActivity.this, InsurenceDashboardActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InsurenceDashboardActivity.this);
                        binding.rclrNearby.setLayoutManager(linearLayoutManager);
                        binding.rclrNearby.setAdapter(insurenceNearbyListadapter);

                    }

                } else {

                    Toast.makeText(InsurenceDashboardActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

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
        Intent i = new Intent(InsurenceDashboardActivity.this, InsurenceAgentsDetails.class);
        i.putExtra("service_id", recommendedList.get(postion).getId());
        i.putExtra("service_name",recommendedList.get(postion).getName());
        startActivity(i);
    }

    @Override
    public void onNearByItemClicked(int postion) {
        Intent i = new Intent(InsurenceDashboardActivity.this, InsurenceDetailsActivity.class);
        i.putExtra("agent_id", nearByList.get(postion).getId());
        startActivity(i);
    }
}