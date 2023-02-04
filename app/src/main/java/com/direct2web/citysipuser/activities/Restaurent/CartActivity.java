package com.direct2web.citysipuser.activities.Restaurent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.Cart.CartAdapter;
import com.direct2web.citysipuser.databinding.ActivityCartBinding;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.CartItem;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseCart;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseCartOperation;
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

public class CartActivity extends AppCompatActivity implements CartAdapter.onPlusButtonClicked, CartAdapter.onMinusButtonClicked {

    ActivityCartBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    ProgressDialog pd;
    List<CartItem> cartItems = new ArrayList<>();
    CartAdapter cartAdapter;
    String couponCode = "", c_id = "";
    String getPrice = "";
    String value, discount = "", code, method;
    SharedPreferences sharedPreferences;
    int a = 0, b = 0;
    private String itemTotle;
    String discountAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        sessionManager = new SessionManager(this);


        couponCode = getIntent().getStringExtra("CouponCode");
        binding.txtCouponsCode.setText(couponCode);
        discountAmount = getIntent().getStringExtra("discount");


        discount = getIntent().getStringExtra("discount");
        binding.txtDiscount.setText(discountAmount);
       // Toast.makeText(this, "discount" + discountAmount, Toast.LENGTH_SHORT).show();

        if (binding.txtCouponsCode.getText().equals("")) {
            binding.btnDelete.setVisibility(View.GONE);
            binding.txtDiscount.setText("0");
        } else if (binding.txtCouponsCode.getText().length() > 0){
            binding.btnDelete.setVisibility(View.VISIBLE);
            binding.txtDiscount.setText(discountAmount);
        }
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.txtCouponsCode.setText("");
                binding.txtDiscount.setText("0");
                Toast.makeText(CartActivity.this, "PromoCode Removed...", Toast.LENGTH_SHORT).show();
                binding.btnDelete.setVisibility(View.GONE);
                if (!binding.txtTotle.getText().toString().equals("")){
                    a = Integer.parseInt(binding.txtTotle.getText().toString());
                }

                if (!binding.txtDiscount.getText().toString().equals("")) {
                    b = Integer.parseInt(binding.txtDiscount.getText().toString());
                }
                String c = String.valueOf(a-b);

                binding.txtTotlePay.setText(c);

            }
        });
       /* SharedPreferences Preferences1 = getApplicationContext().getSharedPreferences("offerDiscount", Context.MODE_PRIVATE);
        if (binding.txtTotle.getText().equals("")) {
            binding.txtDiscount.setText("0");
        } else {
            binding.txtDiscount.setText(discountAmount);
            Toast.makeText(this, "discount" + discountAmount, Toast.LENGTH_SHORT).show();
        }
*/

        sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgOrder.setColorFilter(getResources().getColor(R.color.clr_f54748));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        value = binding.txtTotlePay.getText().toString();
        discount = binding.txtDiscount.getText().toString();
        code = binding.txtDiscount.getText().toString();
        method = binding.txtCouponsCode.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("amount",value);
        editor.putString("distance",discount);
        editor.putString("code",code);
        editor.putString("method",method);
        editor.commit();

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
                method = binding.txtCouponsCode.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("amount",value);
                editor.putString("distance",discount);
                editor.putString("code",code);
                editor.putString("method",method);
                editor.commit();


                Intent intent = new Intent(CartActivity.this,PaymentMethodActivity.class);
                intent.putExtra("totle_amount",binding.txtTotlePay.getText().toString());
                startActivity(intent);
            }
        });

        binding.txtCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cartItems.size() > 0){

                    StringBuilder sb = new StringBuilder();
                    for (CartItem u : cartItems) {
                        sb.append(u.getProductId()).append("~~");
                    }
                    c_id = sb.toString();
                    c_id = c_id.substring(0, c_id.length() - 2);
                    Log.e("string", c_id);

                }
                Intent intent = new Intent(CartActivity.this,ApplyCouponsActivity.class);
                intent.putExtra("c_id",c_id);
                intent.putExtra("item_totle",itemTotle);
//                Toast.makeText(CartActivity.this, "c_id : " + c_id, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    private void sendCartOperation(String catId,String operation,String qty) {

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponseCartOperation> call = api.sendCartOperation("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,catId,operation,qty);

        call.enqueue(new Callback<ResponseCartOperation>() {
            @Override
            public void onResponse(Call<ResponseCartOperation> call, Response<ResponseCartOperation> response) {

                Log.e("responseCartOperation", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().isError()) {

                        Toast.makeText(CartActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        getRestaurentDetails(sessionManager.getUserId());
                        Toast.makeText(CartActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(CartActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseCartOperation> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    private void getRestaurentDetails(String userId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseCart> call = api.getCartItem("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, userId);

        call.enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(Call<ResponseCart> call, Response<ResponseCart> response) {

                Log.e("responseOrder", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().isError()) {

                        Toast.makeText(CartActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        cartItems = response.body().getCart();

                        if (cartItems.size() != 0) {

                            binding.llError.setVisibility(View.GONE);
                            binding.rclrCart.setVisibility(View.VISIBLE);
                            binding.llDetails.setVisibility(View.VISIBLE);

                            cartAdapter = new CartAdapter(cartItems, CartActivity.this, CartActivity.this, CartActivity.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this);
                            binding.rclrCart.setLayoutManager(linearLayoutManager);
                            binding.rclrCart.setAdapter(cartAdapter);
                            getPrice = response.body().getTotel();
                             itemTotle = response.body().getTotel();

                            if (getPrice.equals("")) {
                                binding.txtTotle.setText("0");
                            } else {
                                binding.txtTotle.setText(getPrice);
                            }


                            if (!binding.txtTotle.getText().toString().equals("")) {
                                a = Integer.parseInt(binding.txtTotle.getText().toString());
                            }

                            if (!binding.txtDiscount.getText().toString().equals("")) {
                                b = Integer.parseInt(binding.txtDiscount.getText().toString());
                            }
                            String c = String.valueOf(a - b);

                            binding.txtTotlePay.setText(c);
                        } else {

                            binding.llError.setVisibility(View.VISIBLE);
                            binding.rclrCart.setVisibility(View.GONE);
                            binding.llDetails.setVisibility(View.GONE);


                        }
                    }

                } else {

                    Toast.makeText(CartActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseCart> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onPlus(int postion) {
        sendCartOperation(cartItems.get(postion).getId(),"2",cartItems.get(postion).getQty());
    }

    @Override
    public void onMinus(int postion) {
       if (cartItems.get(postion).getQty().equals("1")) {

           sendCartOperation(cartItems.get(postion).getId(), "1", cartItems.get(postion).getQty());

       } else {
           sendCartOperation(cartItems.get(postion).getId(),"1",cartItems.get(postion).getQty());
       }

    }


}