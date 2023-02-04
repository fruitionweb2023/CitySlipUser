package com.direct2web.citysipuser.activities.Lawyer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorApplyCouponsActivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorCartActivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorPaymentMethodactivity;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorCartAdapter;
import com.direct2web.citysipuser.adapters.LawyerAdapter.LawyerCartAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorYourAppointmentsBinding;
import com.direct2web.citysipuser.databinding.ActivityLawyerCartBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseDoctorAppointStatus;
import com.direct2web.citysipuser.model.LawyerModels.LawyerCart.Cart;
import com.direct2web.citysipuser.model.LawyerModels.LawyerCart.ResponseLawyerAppointStatus;
import com.direct2web.citysipuser.model.LawyerModels.LawyerCart.ResponseLawyerCartItem;
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

public class LawyerCartActivity extends AppCompatActivity {

    ActivityLawyerCartBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    ProgressDialog pd;
    LawyerCartAdapter cartAdapter;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lawyer_cart);
        sessionManager = new SessionManager(this);

        /*couponCode = getIntent().getStringExtra("CouponCode");
        discountOffer = getIntent().getStringExtra("discount");
        binding.txtCouponsCode.setText(couponCode);


        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("offerDiscount", Context.MODE_PRIVATE);
        binding.txtDiscount.setText(sharedPreferences1.getString("discount",""));*/


        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgOrder.setColorFilter(getResources().getColor(R.color.clr_343347));

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
                code = binding.txtDiscount.getText().toString();
              //  method = binding.txtCouponsCode.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("amount",value);
                editor.putString("distance",discount);
                editor.putString("code",code);
                editor.putString("method",method);
                editor.commit();

                sendChckOutDetails(sessionManager.getUserId(),value,"","","");
                Intent intent = new Intent(LawyerCartActivity.this, LawyerAppointmentBookedActivity.class);
                startActivity(intent);
            }
        });

        /*binding.txtCoupons.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(LawyerCartActivity.this, DoctorApplyCouponsActivity.class);
                intent.putExtra("c_id",c_id);
                Toast.makeText(LawyerCartActivity.this, "c_id : " + c_id, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });*/


    }

    private void getRestaurentDetails(String userId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseLawyerCartItem> call = api.getLawyerCartItem("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, userId);

        call.enqueue(new Callback<ResponseLawyerCartItem>() {
            @Override
            public void onResponse(Call<ResponseLawyerCartItem> call, Response<ResponseLawyerCartItem> response) {

                Log.e("responcecartLawyer", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(LawyerCartActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        if (cartItems.size() < 0) {
                            binding.llError.setVisibility(View.VISIBLE);
                            binding.scrollView.setVisibility(View.GONE);
                            Toast.makeText(LawyerCartActivity.this, "Please add services in cart...", Toast.LENGTH_SHORT).show();
                        } else {
                            binding.llError.setVisibility(View.GONE);
                            binding.scrollView.setVisibility(View.VISIBLE);

                            cartItems = response.body().getCart();
                            cartAdapter = new LawyerCartAdapter(cartItems, LawyerCartActivity.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LawyerCartActivity.this);
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

                    Toast.makeText(LawyerCartActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseLawyerCartItem> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    public void sendChckOutDetails(String userId, String totalAmount,String couponCode, String discount,String method){

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponseLawyerAppointStatus> call = api.sendLawyerCheckoutDetails("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,userId,totalAmount,discount,couponCode,method);
        call.enqueue(new Callback<ResponseLawyerAppointStatus>() {
            @Override
            public void onResponse(Call<ResponseLawyerAppointStatus> call, Response<ResponseLawyerAppointStatus> response) {

                Log.e("responseDoctorCheckout", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(LawyerCartActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                    } else {

                       /* sp = getSharedPreferences("orderStatus",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("orderId",response.body().getOrderId());
                        editor.putString("eta",response.body().getEta());
                        editor.commit();*/

                        Toast.makeText(LawyerCartActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLawyerAppointStatus> call, Throwable t) {
                t.printStackTrace();
                Log.e("errorCheckout", t.getMessage());
            }
        });
    }
}