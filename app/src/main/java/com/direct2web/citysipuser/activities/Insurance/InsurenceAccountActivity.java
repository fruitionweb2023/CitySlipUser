package com.direct2web.citysipuser.activities.Insurance;

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
import com.direct2web.citysipuser.databinding.ActivityInsurenceAccountBinding;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.SessionManager;

public class InsurenceAccountActivity extends AppCompatActivity {

    ActivityInsurenceAccountBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurence_account);

        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_2C3564));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));



        binding.txtMyInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InsurenceAccountActivity.this, InsurenceMyInformationActivity.class);
                startActivity(intent);

            }
        });

        binding.txtMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InsurenceAccountActivity.this, InsurenceMyAddressActivity.class);
                startActivity(intent);

            }
        });

        binding.txtOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(InsurenceAccountActivity.this, "Clicked...", Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(InsurenceAccountActivity.this, InsurenceOffersActivity.class);
                startActivity(intent);*/

            }
        });


        binding.txtHelpAndFaqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InsurenceAccountActivity.this, InsurenceHelpAndFAQSActivity.class);
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
                Toast.makeText(InsurenceAccountActivity.this, "Clicked...", Toast.LENGTH_SHORT).show();
            }
        });

        binding.txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsurenceAccountActivity.this, InsurenceSettingsActivity.class);
                startActivity(intent);
            }
        });

        binding.txtFavouriteRestaurent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(InsurenceAccountActivity.this, "Clicked...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InsurenceAccountActivity.this, InsurenceFavouriteActivity.class);
                startActivity(intent);
            }
        });

    }
}