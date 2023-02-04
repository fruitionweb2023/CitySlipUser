package com.direct2web.citysipuser.activities.Restaurent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.BusinessList.BusinessListNearbyAdapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.BusinessList.RestaurentListAdapter;
import com.direct2web.citysipuser.databinding.ActivityMainBinding;
import com.direct2web.citysipuser.databinding.FilterByBottomSheetBinding;
import com.direct2web.citysipuser.databinding.SortListBottomsheetBinding;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessList.BusinessList;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessList.NearByList;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessList.RecommendedList;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.CartItem;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseCart;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RestaurentListAdapter.OnItemClickListner, BusinessListNearbyAdapter.OnItemClickListnerNearBy {

    ActivityMainBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    List<RecommendedList> businessList = new ArrayList<>();
    List<NearByList> businessListNearBy = new ArrayList<>();
    RestaurentListAdapter adapter;
    BusinessListNearbyAdapter businessListNearbyAdapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_f54748));
        binding.bottomnavigation.bbTxtHome.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));


        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        getRestaurentDetails();
        getNearByRestaurentDetails();

        binding.txtSortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                SortListBottomsheetBinding binding2 = DataBindingUtil.inflate(layoutInflater, R.layout.sort_list_bottomsheet, null, false);
                bottomSheetDialog.setContentView(binding2.getRoot());
                bottomSheetDialog.show();
                binding2.btnGetStarted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.hide();
                    }
                });
            }
        });

        binding.txtFilterBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                FilterByBottomSheetBinding binding3 = DataBindingUtil.inflate(layoutInflater, R.layout.filter_by_bottom_sheet, null, false);
                bottomSheetDialog.setContentView(binding3.getRoot());
                bottomSheetDialog.show();
                binding3.btnGetStarted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.hide();
                    }
                });

            }
        });

        binding.txtSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RestaurentRecommendedListActivity.class);
                startActivity(i);
            }
        });

    }


    private void getNearByRestaurentDetails() {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<BusinessList> call = api.getRestaurentDetails("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, "6", "", "", "", "");

        call.enqueue(new Callback<BusinessList>() {
            @Override
            public void onResponse(Call<BusinessList> call, Response<BusinessList> response) {

                Log.e("responseOrder", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        businessListNearBy = response.body().getNearByList();
                        businessListNearbyAdapter = new BusinessListNearbyAdapter(businessListNearBy, MainActivity.this, MainActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                        binding.rclrNearby.setLayoutManager(linearLayoutManager);
                        binding.rclrNearby.setAdapter(businessListNearbyAdapter);

                    }

                } else {

                    Toast.makeText(MainActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<BusinessList> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    private void getRestaurentDetails() {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<BusinessList> call = api.getRestaurentDetails("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, "6", "", "", "", "");

        call.enqueue(new Callback<BusinessList>() {
            @Override
            public void onResponse(Call<BusinessList> call, Response<BusinessList> response) {

                Log.e("responseOrder", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        businessList = response.body().getRecommendedList();
                        adapter = new RestaurentListAdapter(businessList, MainActivity.this, MainActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                        binding.rclrRecommanded.setLayoutManager(linearLayoutManager);
                        binding.rclrRecommanded.setAdapter(adapter);

                    }

                } else {

                    Toast.makeText(MainActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<BusinessList> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onItemClicked(int postion) {
        Intent i = new Intent(MainActivity.this, RestaurantDetailsActivity.class);
        i.putExtra("business_id", businessList.get(postion).getId());
        startActivity(i);
    }

    @Override
    public void onNearByItemClicked(int postion) {
        Intent i = new Intent(MainActivity.this, RestaurantDetailsActivity.class);
        i.putExtra("business_id", businessListNearBy.get(postion).getId());
        startActivity(i);
    }

    public void setCartItem(String uid) {


        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponseCart> call = api.getCartItem("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, uid);
        call.enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(Call<ResponseCart> call, Response<ResponseCart> response) {

                if (response.body() != null && response.isSuccessful()) {
                    List<CartItem> cartItems = (ArrayList<CartItem>) response.body().getCart();

                    if (cartItems != null) {
                        if (cartItems.size() > 0) {
                            binding.bottomnavigation.toolbarTxtNumber.setVisibility(View.VISIBLE);
                            binding.bottomnavigation.toolbarTxtNumber.setText(String.valueOf(cartItems.size()));

                        } else {
                            binding.bottomnavigation.toolbarTxtNumber.setVisibility(View.GONE);

                        }
                    } else {
                        binding.bottomnavigation.toolbarTxtNumber.setVisibility(View.GONE);

                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseCart> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setCartItem(new SessionManager(this).getUserId());
    }

}