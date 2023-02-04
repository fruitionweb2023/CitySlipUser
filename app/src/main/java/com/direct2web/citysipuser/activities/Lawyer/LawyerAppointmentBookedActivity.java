package com.direct2web.citysipuser.activities.Lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorServicesAndReviewsActivity;
import com.direct2web.citysipuser.databinding.ActivityDoctorAppointmentBookedBinding;
import com.direct2web.citysipuser.databinding.ActivityLawyerAppointmentBookedBinding;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.SessionManager;

public class LawyerAppointmentBookedActivity extends AppCompatActivity {

    ActivityLawyerAppointmentBookedBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lawyer_appointment_booked);
        sessionManager = new SessionManager(this);

        binding.btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LawyerAppointmentBookedActivity.this, LawyerServicesAndReviewsActivity.class);
                startActivity(intent);
            }
        });
    }
}