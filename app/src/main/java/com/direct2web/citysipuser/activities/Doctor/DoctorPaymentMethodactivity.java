package com.direct2web.citysipuser.activities.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Restaurent.OrderConfirmedActivity;
import com.direct2web.citysipuser.activities.Restaurent.PaymentMethodActivity;
import com.direct2web.citysipuser.databinding.ActivityDoctorPaymentMethodactivityBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseDoctorAppointStatus;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseCheckOut;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorPaymentMethodactivity extends AppCompatActivity {

    ActivityDoctorPaymentMethodactivityBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_payment_methodactivity);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);
        binding.bottomnavigation.bbImgOrder.setColorFilter(getResources().getColor(R.color.clr_0059C8));
        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DoctorCartData", Context.MODE_PRIVATE);
        String a = sharedPreferences.getString("amount","");
        String b = sharedPreferences.getString("distance","");
        String c = sharedPreferences.getString("code","");
        String d = sharedPreferences.getString("method","");

        Toast.makeText(this, "all Value : " + a + b + c + d, Toast.LENGTH_SHORT).show();


        binding.txtPrice.setText(a);

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChckOutDetails(sessionManager.getUserId(),a,c,b,d);

            }
        });

    }

    public void sendChckOutDetails(String userId, String totalAmount,String couponCode, String discount,String method){

        Log.e("sendDoctorCheckout",userId+", "+totalAmount+", "+couponCode+", "+discount+", "+method);

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponseDoctorAppointStatus> call = api.sendDoctorCheckoutDetails("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,userId,totalAmount,discount,couponCode,method);
        call.enqueue(new Callback<ResponseDoctorAppointStatus>() {
            @Override
            public void onResponse(Call<ResponseDoctorAppointStatus> call, Response<ResponseDoctorAppointStatus> response) {

                Log.e("responseDoctorCheckout", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorPaymentMethodactivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                    } else {

                        Intent intent = new Intent(DoctorPaymentMethodactivity.this, DoctorAppointmentBookedActivity.class);
                        startActivity(intent);
                       /* sp = getSharedPreferences("orderStatus",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("orderId",response.body().getOrderId());
                        editor.putString("eta",response.body().getEta());
                        editor.commit();*/

                        Toast.makeText(DoctorPaymentMethodactivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorAppointStatus> call, Throwable t) {
                t.printStackTrace();
                Log.e("errorCheckout", t.getMessage());
                Toast.makeText(DoctorPaymentMethodactivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();
            }
        });
    }
}