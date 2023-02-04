package com.direct2web.citysipuser.activities.Restaurent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.BusinessList.BusinessListNearbyAdapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.BusinessList.RestaurentListAdapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.BusinessList.RestaurentSeeAllListAdapter;
import com.direct2web.citysipuser.databinding.ActivityRestaurentRecommendedListBinding;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessList.BusinessList;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessList.RecommendedList;
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

public class RestaurentRecommendedListActivity extends AppCompatActivity implements RestaurentSeeAllListAdapter.OnItemClickListner {

    ActivityRestaurentRecommendedListBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    List<RecommendedList> businessList = new ArrayList<>();
    ProgressDialog pd;
    RestaurentSeeAllListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurent_recommended_list);
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

                        Toast.makeText(RestaurentRecommendedListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        businessList = response.body().getRecommendedList();
                        adapter = new RestaurentSeeAllListAdapter(businessList, RestaurentRecommendedListActivity.this, RestaurentRecommendedListActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RestaurentRecommendedListActivity.this);
                        binding.rclrRfy.setLayoutManager(linearLayoutManager);
                        binding.rclrRfy.setAdapter(adapter);

                    }

                } else {

                    Toast.makeText(RestaurentRecommendedListActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

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
        Intent i = new Intent(RestaurentRecommendedListActivity.this, RestaurantDetailsActivity.class);
        i.putExtra("business_id", businessList.get(postion).getId());
        startActivity(i);
    }
}