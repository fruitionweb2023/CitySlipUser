package com.direct2web.citysipuser.activities.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.CommonActivies.SettingActivity;
import com.direct2web.citysipuser.databinding.ActivityDoctorAccountBinding;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.SessionManager;

public class DoctorAccountActivity extends AppCompatActivity {

    ActivityDoctorAccountBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_account);

        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_0059C8));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));



        binding.txtAppointment.setOnClickListener(view -> {

            Intent intent = new Intent(DoctorAccountActivity.this, DoctorAppointmentActivity.class);
            startActivity(intent);

        });

        binding.txtSetting.setOnClickListener(view -> {

            Intent intent = new Intent(DoctorAccountActivity.this, SettingActivity.class);
            startActivity(intent);

        });


        binding.txtMyInformation.setOnClickListener(view -> {

            Intent intent = new Intent(DoctorAccountActivity.this, DoctorMyInformationActivity.class);
            startActivity(intent);

        });

        binding.txtMyAddress.setOnClickListener(view -> {

            Intent intent = new Intent(DoctorAccountActivity.this, DoctorMyAddressActivity.class);
            startActivity(intent);

        });

        binding.txtOffers.setOnClickListener(view -> {

            Intent intent = new Intent(DoctorAccountActivity.this, DoctorOffersActivity.class);
            startActivity(intent);

        });


        binding.txtHelpAndFaqs.setOnClickListener(view -> {

            Intent intent = new Intent(DoctorAccountActivity.this, DoctorHelpAndFAQSActivity.class);
            startActivity(intent);

        });

        binding.btnLogout.setOnClickListener(v -> {

            sessionManager.LogOut();
            sessionManager.setLogin(false);

        });

        binding.txtCitysipMoney.setOnClickListener(view -> Toast.makeText(DoctorAccountActivity.this, "Clicked...", Toast.LENGTH_SHORT).show());

       /* binding.txtSetting.setOnClickListener(view -> {
            Intent intent = new Intent(DoctorAccountActivity.this, DoctorSettingsActivity.class);
            startActivity(intent);
        });*/

        binding.txtFavouriteRestaurent.setOnClickListener(view -> {
            Intent intent = new Intent(DoctorAccountActivity.this, DoctorFavouriteHospitalActivity.class);
            startActivity(intent);
        });

    }
}