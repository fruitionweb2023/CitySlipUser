package com.direct2web.citysipuser.activities.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorRatingAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorRatingBinding;
import com.direct2web.citysipuser.model.DoctorModels.DoctorReview;
import com.direct2web.citysipuser.model.DoctorModels.Review;
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

public class DoctorRatingActivity extends AppCompatActivity {

    ActivityDoctorRatingBinding binding;
    SessionManager sessionManager;
    ProgressDialog pd;
    String businessId = "";
    BottomButtonClickListner bottomButtonClickListner;
    DoctorRatingAdapter doctorRatingAdapter;
    List<Review> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_rating);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_f54748));
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
    }

    private void getRestaurentDetails(String businessId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<DoctorReview> call = api.getDoctorreview("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, businessId);

        call.enqueue(new Callback<DoctorReview>() {
            @Override
            public void onResponse(Call<DoctorReview> call, Response<DoctorReview> response) {

                Log.e("responseReview", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorRatingActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        binding.txtOverallRating.setText(response.body().getOverallRatings());
                        binding.txtTotelRating.setText(response.body().getTotalRating());

                        reviewList = response.body().getReviews();
                        doctorRatingAdapter = new DoctorRatingAdapter(reviewList, DoctorRatingActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorRatingActivity.this);
                        binding.rcleRating.setLayoutManager(linearLayoutManager);
                        binding.rcleRating.setAdapter(doctorRatingAdapter);
                    }

                } else {

                    Toast.makeText(DoctorRatingActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DoctorReview> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}