package com.direct2web.citysipuser.activities.Restaurent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.ActivityOrderConfirmedBinding;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.SessionManager;

public class OrderConfirmedActivity extends AppCompatActivity {

    ActivityOrderConfirmedBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_confirmed);
        sessionManager = new SessionManager(this);


        binding.btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(OrderConfirmedActivity.this, OrderStatusActivity.class);
               startActivity(intent);
            }
        });
    }
}