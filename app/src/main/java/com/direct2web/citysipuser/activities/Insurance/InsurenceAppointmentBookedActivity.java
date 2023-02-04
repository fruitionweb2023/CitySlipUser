package com.direct2web.citysipuser.activities.Insurance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorServicesAndReviewsActivity;
import com.direct2web.citysipuser.databinding.ActivityDoctorAppointmentBookedBinding;
import com.direct2web.citysipuser.databinding.ActivityInsurenceAppointmentBookedBinding;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.SessionManager;

public class InsurenceAppointmentBookedActivity extends AppCompatActivity {

    ActivityInsurenceAppointmentBookedBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurence_appointment_booked);
        sessionManager = new SessionManager(this);

        binding.btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(InsurenceAppointmentBookedActivity.this, InsurenceServicesAndReviewsActivity.class);
                startActivity(intent);
            }
        });
    }
}