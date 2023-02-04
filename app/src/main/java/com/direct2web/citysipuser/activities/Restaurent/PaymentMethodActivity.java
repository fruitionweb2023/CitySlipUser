package com.direct2web.citysipuser.activities.Restaurent;

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
import com.direct2web.citysipuser.databinding.ActivityPaymentMethodBinding;
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

public class PaymentMethodActivity extends AppCompatActivity {

    ActivityPaymentMethodBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_method);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);
        binding.bottomnavigation.bbImgOrder.setColorFilter(getResources().getColor(R.color.clr_f54748));
        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myData", Context.MODE_PRIVATE);
        String a = sharedPreferences.getString("amount","");
        String b = sharedPreferences.getString("distance","");
        String c = sharedPreferences.getString("code","");
        String d = sharedPreferences.getString("method","");

        sendChckOutDetails(sessionManager.getUserId(),a,b,c,d);

        binding.txtPrice.setText(a);


        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentMethodActivity.this,OrderConfirmedActivity.class);
                startActivity(intent);
            }
        });
    }


    public void sendChckOutDetails(String userId, String totalAmount, String discount, String couponCode, String method){

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponseCheckOut> call = api.sendCheckoutDetails("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,userId,totalAmount,discount,couponCode,method);
        call.enqueue(new Callback<ResponseCheckOut>() {
            @Override
            public void onResponse(Call<ResponseCheckOut> call, Response<ResponseCheckOut> response) {

                Log.e("responseCheckout", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().isError()) {

                        Toast.makeText(PaymentMethodActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                    } else {

                        sp = getSharedPreferences("orderStatus",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("orderId",response.body().getOrderId());
                        editor.putString("eta",response.body().getEta());
                        editor.commit();

                        Toast.makeText(PaymentMethodActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }/* else {
                    Toast.makeText(PaymentMethodActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onFailure(Call<ResponseCheckOut> call, Throwable t) {
                t.printStackTrace();
                Log.e("errorCheckout", t.getMessage());
            }
        });
    }
}