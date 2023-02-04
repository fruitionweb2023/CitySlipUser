package com.direct2web.citysipuser.activities.Lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorReviewSubmitedActivity;
import com.direct2web.citysipuser.databinding.ActivityDoctorServicesAndReviewsBinding;
import com.direct2web.citysipuser.databinding.ActivityLawyerServicesAndReviewsBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseDoctorSubmitReview;
import com.direct2web.citysipuser.model.LawyerModels.LawyerCart.ResponseLawyerSubmitReview;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LawyerServicesAndReviewsActivity extends AppCompatActivity {


    ActivityLawyerServicesAndReviewsBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lawyer_services_and_reviews);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgOrder.setColorFilter(getResources().getColor(R.color.clr_0059C8));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        binding.btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float getrating = binding.ratingBar.getRating();
                String feedback = binding.txtFeedback.getText().toString();
                sendReview(sessionManager.getUserId(),feedback,getrating);
                Intent intent = new Intent(LawyerServicesAndReviewsActivity.this, LawyerReviewSubmitedActivity.class);
                startActivity(intent);
            }
        });

    }

    public void sendReview(String userId, String comment, float rating){

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponseLawyerSubmitReview> call = api.sendLawyerReview("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,userId,comment,rating);
        call.enqueue(new Callback<ResponseLawyerSubmitReview>() {
            @Override
            public void onResponse(Call<ResponseLawyerSubmitReview> call, Response<ResponseLawyerSubmitReview> response) {

                Log.e("responseCheckout", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(LawyerServicesAndReviewsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                    } else {

                        Toast.makeText(LawyerServicesAndReviewsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }/* else {
                    Toast.makeText(PaymentMethodActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onFailure(Call<ResponseLawyerSubmitReview> call, Throwable t) {
                t.printStackTrace();
                Log.e("errorCheckout", t.getMessage());
            }
        });
    }


}