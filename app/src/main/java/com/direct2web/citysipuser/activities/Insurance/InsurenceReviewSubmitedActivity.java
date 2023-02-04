package com.direct2web.citysipuser.activities.Insurance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorDashboardActivity;
import com.direct2web.citysipuser.databinding.ActivityDoctorReviewSubmitedBinding;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.SessionManager;

public class InsurenceReviewSubmitedActivity extends AppCompatActivity {


    ActivityDoctorReviewSubmitedBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_review_submited);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgOrder.setColorFilter(getResources().getColor(R.color.clr_2C3564));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        binding.btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsurenceReviewSubmitedActivity.this, InsurenceDashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}