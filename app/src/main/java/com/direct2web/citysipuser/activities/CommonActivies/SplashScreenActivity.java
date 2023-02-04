package com.direct2web.citysipuser.activities.CommonActivies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.ActivitySplashScreenBinding;
import com.direct2web.citysipuser.utils.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {

    ActivitySplashScreenBinding binding;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        sessionManager = new SessionManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (new SessionManager(SplashScreenActivity.this).checkLogin()) {
                    startActivity(new Intent(SplashScreenActivity.this, CategoryActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, IntroLoginActivity.class));
                    finish();
                }
            }
        }, 2000);


    }
}