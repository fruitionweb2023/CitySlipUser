package com.direct2web.citysipuser.activities.Restaurent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorSearchActivity;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorSearchHospitalAdapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.RestaurentSearchHospitalAdapter;
import com.direct2web.citysipuser.databinding.ActivitySearchBinding;
import com.direct2web.citysipuser.model.DoctorModels.ResponseDoctorSearch;
import com.direct2web.citysipuser.model.RestaurentModels.ResponseRestaurentSearch;
import com.direct2web.citysipuser.model.RestaurentModels.WishListBusiness;
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

public class SearchActivity extends AppCompatActivity implements RestaurentSearchHospitalAdapter.OnItemClickListnerNearBy {

    SessionManager sessionManager;
    ActivitySearchBinding binding;
    BottomButtonClickListner bottomButtonClickListner;
    List<WishListBusiness> businessList = new ArrayList<>();
    RestaurentSearchHospitalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgMyBusiness.setColorFilter(getResources().getColor(R.color.clr_f54748));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        binding.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRestaurentDetails(sessionManager.getBusinessType(),binding.editText.getText().toString());
                // filter(binding.editText.getText().toString());

            }
        });



    }


    private void getRestaurentDetails(String catId,String keyWord) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseRestaurentSearch> call = api.searchReataurent("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, catId,keyWord);

        call.enqueue(new Callback<ResponseRestaurentSearch>() {
            @Override
            public void onResponse(Call<ResponseRestaurentSearch> call, Response<ResponseRestaurentSearch> response) {

                Log.e("responseFavRestaurent", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(SearchActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        businessList = response.body().getWishListBusiness();

                        adapter = new RestaurentSearchHospitalAdapter(businessList, SearchActivity.this,SearchActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                        binding.searchRest.setLayoutManager(linearLayoutManager);
                        binding.searchRest.setAdapter(adapter);

                    }

                } else {

                    Toast.makeText(SearchActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseRestaurentSearch> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onNearByItemClicked(int postion) {
        Intent i = new Intent(SearchActivity.this, RestaurantDetailsActivity.class);
        i.putExtra("business_id", businessList.get(postion).getId());
        startActivity(i);
    }
}