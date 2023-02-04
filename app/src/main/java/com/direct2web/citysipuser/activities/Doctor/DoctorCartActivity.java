package com.direct2web.citysipuser.activities.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorCartAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorYourAppointmentsBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.Cart;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseDoctorCartItem;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorCartActivity extends AppCompatActivity {

    ActivityDoctorYourAppointmentsBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    ProgressDialog pd;
    DoctorCartAdapter cartAdapter;
    List<Cart> cartItems = new ArrayList<>();
    String couponCode = "",c_id = "",discountOffer = "";
    String getPrice = "";
    String value,discount,code,method;
    SharedPreferences sharedPreferences;
    int a = 0 , b=0;
    Handler handler = new Handler();
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_your_appointments);
        sessionManager = new SessionManager(this);

        couponCode = getIntent().getStringExtra("CouponCode");
        discountOffer = getIntent().getStringExtra("discount");
        binding.txtCouponsCode.setText(couponCode);


        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("offerDiscount", Context.MODE_PRIVATE);

        if (binding.txtTotle.getText().equals("")) {
            binding.txtDiscount.setText("0");
        } else {
            binding.txtDiscount.setText(sharedPreferences1.getString("discount", ""));
        }

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgOrder.setColorFilter(getResources().getColor(R.color.clr_0059C8));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        sharedPreferences = getSharedPreferences("DoctorCartData",Context.MODE_PRIVATE);

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

       /* refresh = new Runnable() {
            public void run() {

                handler.postDelayed(refresh, 5000);
            }
        };
        handler.post(refresh);*/

        getRestaurentDetails(sessionManager.getUserId());


        if (!binding.txtTotle.getText().toString().equals("")){
            a = Integer.parseInt(binding.txtTotle.getText().toString());
        }

        if (!binding.txtDiscount.getText().toString().equals("")) {
            b = Integer.parseInt(binding.txtDiscount.getText().toString());
        }
        String c = String.valueOf(a-b);

        binding.txtTotlePay.setText(c);

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                value = binding.txtTotlePay.getText().toString();
                discount = binding.txtDiscount.getText().toString();
                code = binding.txtCouponsCode.getText().toString();
                method = "";

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("amount",value);
                editor.putString("distance",discount);
                editor.putString("code",code);
                editor.putString("method",method);
                editor.commit();

                Intent intent = new Intent(DoctorCartActivity.this, DoctorPaymentMethodactivity.class);
                startActivity(intent);
            }
        });

        binding.txtCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cartItems.size() > 0){

                    StringBuilder sb = new StringBuilder();
                    for (Cart u : cartItems) {
                        sb.append(u.getProductId()).append("~~");
                    }
                    c_id = sb.toString();
                    c_id = c_id.substring(0, c_id.length() - 2);
                    Log.e("string", c_id);

                }
                Intent intent = new Intent(DoctorCartActivity.this, DoctorApplyCouponsActivity.class);
                intent.putExtra("c_id",c_id);
               // Toast.makeText(DoctorCartActivity.this, "c_id : " + c_id, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


    }

    private void getRestaurentDetails(String userId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseDoctorCartItem> call = api.getDoctorCartItem("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, userId);

        call.enqueue(new Callback<ResponseDoctorCartItem>() {
            @Override
            public void onResponse(Call<ResponseDoctorCartItem> call, Response<ResponseDoctorCartItem> response) {

                Log.e("responcecart", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorCartActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        if (cartItems.size() < 0) {
                            binding.llError.setVisibility(View.VISIBLE);
                            binding.scrollView.setVisibility(View.GONE);
                            Toast.makeText(DoctorCartActivity.this, "Please add services in cart...", Toast.LENGTH_SHORT).show();
                        } else {
                            binding.llError.setVisibility(View.GONE);
                            binding.scrollView.setVisibility(View.VISIBLE);

                            cartItems = response.body().getCart();
                            cartAdapter = new DoctorCartAdapter(cartItems, DoctorCartActivity.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorCartActivity.this);
                            binding.rclrCart.setLayoutManager(linearLayoutManager);
                            binding.rclrCart.setAdapter(cartAdapter);
                            getPrice = String.valueOf(response.body().getTotalAmount());

                            if (getPrice.equals("")) {
                                binding.txtTotle.setText("0");
                            } else  {
                                binding.txtTotle.setText(getPrice);
                            }


                            if (!binding.txtTotle.getText().toString().equals("")){
                                a = Integer.parseInt(binding.txtTotle.getText().toString());
                            }

                            if (!binding.txtDiscount.getText().toString().equals("")) {
                                b = Integer.parseInt(binding.txtDiscount.getText().toString());
                            }
                            String c = String.valueOf(a-b);

                            binding.txtTotlePay.setText(c);
                        }
                        }


                } else {

                    Toast.makeText(DoctorCartActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorCartItem> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}