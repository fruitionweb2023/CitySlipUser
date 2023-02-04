package com.direct2web.citysipuser.activities.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorImageListAdapter;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorVideoListAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorHospitalImageactivityBinding;
import com.direct2web.citysipuser.model.DoctorModels.DoctorImageAndVideo;
import com.direct2web.citysipuser.model.DoctorModels.Image;
import com.direct2web.citysipuser.model.DoctorModels.Video;
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

public class DoctorHospitalImageactivity extends AppCompatActivity {

    ActivityDoctorHospitalImageactivityBinding binding;
    SessionManager sessionManager;
    ProgressDialog pd;
    List<Image> imageListHospital = new ArrayList<>();
    List<Video> videoListHospital = new ArrayList<>();
    BottomButtonClickListner bottomButtonClickListner;
    private String businessId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_hospital_imageactivity);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_f54748));
        binding.bottomnavigation.bbTxtHome.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        businessId = getIntent().getStringExtra("businessId");
        getRestaurentDetails(businessId);
    }


    private void getRestaurentDetails(String business) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<DoctorImageAndVideo> call = api.getImagesAndVideosHospital("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, business);

        call.enqueue(new Callback<DoctorImageAndVideo>() {
            @Override
            public void onResponse(Call<DoctorImageAndVideo> call, Response<DoctorImageAndVideo> response) {

                Log.e("responseOrder", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorHospitalImageactivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        imageListHospital = response.body().getImage();
                        videoListHospital = response.body().getVideo();

                        int numberOfColumns = 3;
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(DoctorHospitalImageactivity.this,numberOfColumns);
                        binding.rcleImages.setLayoutManager(gridLayoutManager);
                        DoctorImageListAdapter doctorImageListAdapter = new DoctorImageListAdapter( imageListHospital,DoctorHospitalImageactivity.this);
                        binding.rcleImages.setAdapter(doctorImageListAdapter);

                        int numberOfColumns1 = 3;
                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(DoctorHospitalImageactivity.this,numberOfColumns1);
                        binding.rcleVideos.setLayoutManager(gridLayoutManager1);
                        DoctorVideoListAdapter doctorVideoListAdapter = new DoctorVideoListAdapter(DoctorHospitalImageactivity.this, videoListHospital);
                        binding.rcleVideos.setAdapter(doctorVideoListAdapter);

                    }

                } else {

                    Toast.makeText(DoctorHospitalImageactivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DoctorImageAndVideo> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

}