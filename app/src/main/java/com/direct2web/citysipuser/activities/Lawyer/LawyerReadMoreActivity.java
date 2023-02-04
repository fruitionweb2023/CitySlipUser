package com.direct2web.citysipuser.activities.Lawyer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorHospitalImageactivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorRatingActivity;
import com.direct2web.citysipuser.adapters.DoctorAdapter.AboutHospitalImageAdapter;
import com.direct2web.citysipuser.adapters.LawyerAdapter.LawyerImageAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorHospitalReadMoreBinding;
import com.direct2web.citysipuser.databinding.ActivityLawyerReadMoreBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalReadMore.ResponseDoctorReadMore;
import com.direct2web.citysipuser.model.LawyerModels.ReadMore.Image;
import com.direct2web.citysipuser.model.LawyerModels.ReadMore.ResponseLawyerReadMore;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LawyerReadMoreActivity extends AppCompatActivity implements LawyerImageAdapter.OnImageClickedListner {

    ActivityLawyerReadMoreBinding binding;
    SessionManager sessionManager;
    ProgressDialog pd;
    BottomButtonClickListner bottomButtonClickListner;
    String businessId="";
    List<Image> imageListHospital;
    LawyerImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lawyer_read_more);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);
        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_343347));
        binding.bottomnavigation.bbTxtHome.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        businessId = getIntent().getStringExtra("business_id");
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        getRestaurentDetails(businessId);

        binding.btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LawyerReadMoreActivity.this, LawyerRatingActivity.class);
                intent.putExtra("business_id",businessId);
                startActivity(intent);
            }
        });
    }


    private void getRestaurentDetails(String businessId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseLawyerReadMore> call = api.getLawyerDetailsReadMore("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, businessId);

        call.enqueue(new Callback<ResponseLawyerReadMore>() {
            @Override
            public void onResponse(Call<ResponseLawyerReadMore> call, Response<ResponseLawyerReadMore> response) {

                Log.e("responseOrder", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(LawyerReadMoreActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        binding.txtRestaurentName.setText(response.body().getBusinessName());
                        binding.txtRestaurentDescription.setText(response.body().getDescription());
                        binding.txtRating.setText(response.body().getRatingStar());
                        binding.txtFeatures.setText(response.body().getFeatures());
                        binding.txtLocation.setText(response.body().getLocation());
                        binding.txtMobileNumber.setText(response.body().getContact());
                        binding.txtWebSite.setText(response.body().getWebsite());

                        imageListHospital = response.body().getImages();
                        adapter = new LawyerImageAdapter(imageListHospital, LawyerReadMoreActivity.this, LawyerReadMoreActivity.this);
                        binding.rcleAboutImages.setAdapter(adapter);

                    }

                } else {

                    Toast.makeText(LawyerReadMoreActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseLawyerReadMore> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onImageClicked(int postion) {
        Intent intent = new Intent(LawyerReadMoreActivity.this, LawyerImageactivity.class);
        intent.putExtra("businessId",businessId);
        startActivity(intent);
    }
}