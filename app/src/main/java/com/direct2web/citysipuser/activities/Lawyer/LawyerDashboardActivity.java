package com.direct2web.citysipuser.activities.Lawyer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorHospitalDetailsActivity;
import com.direct2web.citysipuser.adapters.LawyerAdapter.LawyerListAdapter;
import com.direct2web.citysipuser.adapters.LawyerAdapter.LawyerNearbyListadapter;
import com.direct2web.citysipuser.databinding.ActivityLawyerDashboardBinding;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.NearBy;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.Recommended;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.ResponseLawyerList;
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

public class LawyerDashboardActivity extends AppCompatActivity implements LawyerListAdapter.OnItemClickListner, LawyerNearbyListadapter.OnItemClickListnerNearBy {

    ActivityLawyerDashboardBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    ProgressDialog pd;
    LawyerListAdapter adapter;
    LawyerNearbyListadapter lawyerNearbyListadapter;
    List<Recommended> recommendedList = new ArrayList<>();
    List<NearBy> nearByList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lawyer_dashboard);
        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_343347));
        binding.bottomnavigation.bbTxtHome.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        getRestaurentDetails(sessionManager.getBusinessType());
      /*  sliderImageListAdapter = new SliderImageListAdapter(this);
        binding.imageContainer.setAdapter(sliderImageListAdapter);*/

    }

    private void getRestaurentDetails(String businessId) {

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseLawyerList> call = api.getLawyerListDetails("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, businessId, "", "", "", "");

        call.enqueue(new Callback<ResponseLawyerList>() {
            @Override
            public void onResponse(Call<ResponseLawyerList> call, Response<ResponseLawyerList> response) {

                Log.e("responseLawyerList", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(LawyerDashboardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        recommendedList = response.body().getRecommendedList();
                        adapter = new LawyerListAdapter(recommendedList, LawyerDashboardActivity.this, LawyerDashboardActivity.this);
                        binding.rclrRecommanded.setAdapter(adapter);

                        nearByList = response.body().getNearByList();
                        lawyerNearbyListadapter = new LawyerNearbyListadapter(nearByList, LawyerDashboardActivity.this, LawyerDashboardActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LawyerDashboardActivity.this);
                        binding.rclrNearby.setLayoutManager(linearLayoutManager);
                        binding.rclrNearby.setAdapter(lawyerNearbyListadapter);

                    }

                } else {

                    Toast.makeText(LawyerDashboardActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseLawyerList> call, Throwable t) {

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
        Intent i = new Intent(LawyerDashboardActivity.this, LawyerHospitalDetailsActivity.class);
        i.putExtra("business_id", recommendedList.get(postion).getId());
        startActivity(i);
    }

    @Override
    public void onNearByItemClicked(int postion) {
        Intent i = new Intent(LawyerDashboardActivity.this, LawyerHospitalDetailsActivity.class);
        i.putExtra("business_id", nearByList.get(postion).getId());
        startActivity(i);
    }
}