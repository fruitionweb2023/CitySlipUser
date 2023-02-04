package com.direct2web.citysipuser.activities.Lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorFavouriteHospitalActivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorHelpAndFAQSActivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorMyAddressActivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorMyInformationActivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorOffersActivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorSettingsActivity;
import com.direct2web.citysipuser.databinding.ActivityDoctorAccountBinding;
import com.direct2web.citysipuser.databinding.ActivityLawyerAccountBinding;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.SessionManager;

public class LawyerAccountActivity extends AppCompatActivity {

    ActivityLawyerAccountBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lawyer_account);

        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_0059C8));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));



        binding.txtMyInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LawyerAccountActivity.this, LawyerMyInformationActivity.class);
                startActivity(intent);

            }
        });

        binding.txtMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LawyerAccountActivity.this, LawyerMyAddressActivity.class);
                startActivity(intent);

            }
        });

        binding.txtOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(LawyerAccountActivity.this, "Clicked...", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(LawyerAccountActivity.this, DoctorOffersActivity.class);
                startActivity(intent);*/

            }
        });


        binding.txtHelpAndFaqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LawyerAccountActivity.this, LawyerHelpAndFAQSActivity.class);
                startActivity(intent);

            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.LogOut();
                sessionManager.setLogin(false);

            }
        });

        binding.txtCitysipMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LawyerAccountActivity.this, "Clicked...", Toast.LENGTH_SHORT).show();
            }
        });

        binding.txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LawyerAccountActivity.this, LawyerSettingsActivity.class);
                startActivity(intent);
            }
        });

        binding.txtFavouriteRestaurent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LawyerAccountActivity.this, LawyerFavouriteActivity.class);
                startActivity(intent);
            }
        });

    }
}