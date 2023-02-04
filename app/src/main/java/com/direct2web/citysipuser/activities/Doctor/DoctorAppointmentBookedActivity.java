package com.direct2web.citysipuser.activities.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Restaurent.OrderConfirmedActivity;
import com.direct2web.citysipuser.activities.Restaurent.OrderStatusActivity;
import com.direct2web.citysipuser.databinding.ActivityDoctorAppointmentBookedBinding;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.SessionManager;

public class DoctorAppointmentBookedActivity extends AppCompatActivity {

    ActivityDoctorAppointmentBookedBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_appointment_booked);
        sessionManager = new SessionManager(this);

        binding.btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(DoctorAppointmentBookedActivity.this, DoctorServicesAndReviewsActivity.class);
                startActivity(intent);
            }
        });
    }
}