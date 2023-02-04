package com.direct2web.citysipuser.activities.Doctor;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHospitalList.DoctorHospitalListAdapter;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHospitalList.DoctorHospitalNearbyListadapter;
import com.direct2web.citysipuser.adapters.DoctorAdapter.ResponseHomeScreen;
import com.direct2web.citysipuser.adapters.DoctorAdapter.SliderAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorDashboardBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalList.NearBy;
import com.direct2web.citysipuser.model.DoctorModels.HospitalList.Recommended;
import com.direct2web.citysipuser.model.DoctorModels.HospitalList.ResponseDoctorHospitalList;
import com.direct2web.citysipuser.model.DoctorModels.Slider;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDashboardActivity extends AppCompatActivity implements DoctorHospitalListAdapter.OnItemClickListner, DoctorHospitalNearbyListadapter.OnItemClickListnerNearBy {

    ActivityDoctorDashboardBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    ProgressDialog pd;
    DoctorHospitalListAdapter adapter;
    DoctorHospitalNearbyListadapter doctorHospitalNearbyListadapter;
    List<Recommended> recommendedList = new ArrayList<>();
    List<NearBy> nearByList = new ArrayList<>();
    //Slider
    List<Slider> sliderDataArrayList = new ArrayList<>();
    SliderAdapter sliderAdapter;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_dashboard);
        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_0059C8));
        binding.bottomnavigation.bbTxtHome.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        getRestaurentDetails(sessionManager.getBusinessType());

        sliderAdapter = new SliderAdapter(this, sliderDataArrayList);
        binding.slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        binding.slider.setSliderAdapter(sliderAdapter);
        binding.slider.setScrollTimeInSec(4);
        binding.slider.setAutoCycle(true);
        binding.slider.startAutoCycle();

        getSlider(sessionManager.getBusinessType());

    }

    private void getRestaurentDetails(String businessId) {

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseDoctorHospitalList> call = api.getHospitalListDetails("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, businessId, "", "", "", "");

        call.enqueue(new Callback<ResponseDoctorHospitalList>() {
            @Override
            public void onResponse(Call<ResponseDoctorHospitalList> call, Response<ResponseDoctorHospitalList> response) {

                Log.e("responsehospitallist", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorDashboardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        recommendedList = response.body().getRecommendedList();
                        adapter = new DoctorHospitalListAdapter(recommendedList, DoctorDashboardActivity.this, DoctorDashboardActivity.this);
                        binding.rclrRecommanded.setAdapter(adapter);

                        nearByList = response.body().getNearByList();
                        doctorHospitalNearbyListadapter = new DoctorHospitalNearbyListadapter(nearByList, DoctorDashboardActivity.this, DoctorDashboardActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorDashboardActivity.this);
                        binding.rclrNearby.setLayoutManager(linearLayoutManager);
                        binding.rclrNearby.setAdapter(doctorHospitalNearbyListadapter);

                    }

                } else {

                    Toast.makeText(DoctorDashboardActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorHospitalList> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    private void getNearByRestaurentDetails(String businessId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseDoctorHospitalList> call = api.getHospitalListDetails("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, businessId, "", "", "", "");

        call.enqueue(new Callback<ResponseDoctorHospitalList>() {
            @Override
            public void onResponse(Call<ResponseDoctorHospitalList> call, Response<ResponseDoctorHospitalList> response) {

                Log.e("responseOrder", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorDashboardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        nearByList = response.body().getNearByList();
                        doctorHospitalNearbyListadapter = new DoctorHospitalNearbyListadapter(nearByList, DoctorDashboardActivity.this, DoctorDashboardActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorDashboardActivity.this);
                        binding.rclrNearby.setLayoutManager(linearLayoutManager);
                        binding.rclrNearby.setAdapter(doctorHospitalNearbyListadapter);

                    }

                } else {

                    Toast.makeText(DoctorDashboardActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorHospitalList> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    private void getSlider(String catId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseHomeScreen> call = api.getSliderList("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, catId);

        call.enqueue(new Callback<ResponseHomeScreen>() {
            @Override
            public void onResponse(Call<ResponseHomeScreen> call, Response<ResponseHomeScreen> response) {

                Log.e("responseSlider", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorDashboardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        sliderDataArrayList = response.body().getSlider();
                        sliderAdapter.updateList(sliderDataArrayList);

                    }

                } else {

                    Toast.makeText(DoctorDashboardActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseHomeScreen> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("errorSlider", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onItemClicked(int postion) {
        Intent i = new Intent(DoctorDashboardActivity.this, DoctorHospitalDetailsActivity.class);
        i.putExtra("business_id", recommendedList.get(postion).getId());
        startActivity(i);
    }

    @Override
    public void onNearByItemClicked(int postion) {
        Intent i = new Intent(DoctorDashboardActivity.this, DoctorHospitalDetailsActivity.class);
        i.putExtra("business_id", nearByList.get(postion).getId());
        startActivity(i);
    }
}