package com.direct2web.citysipuser.activities.Insurance;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorSearchHospitalAdapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceSearchServicesAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorSearchBinding;
import com.direct2web.citysipuser.databinding.ActivityInsurenceSearchBinding;
import com.direct2web.citysipuser.model.DoctorModels.ResponseDoctorSearch;
import com.direct2web.citysipuser.model.Insurence.Search.ResponseInsurenceSearch;
import com.direct2web.citysipuser.model.Insurence.Search.WishListBusiness;
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

public class InsurenceSearchActivity extends AppCompatActivity {

    ActivityInsurenceSearchBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    List<WishListBusiness> businessList = new ArrayList<>();
    ProgressDialog pd;
    InsurenceSearchServicesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurence_search);

        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgMyBusiness.setColorFilter(getResources().getColor(R.color.clr_2C3564));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));




        binding.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRestaurentDetails(sessionManager.getBusinessType(),binding.editText.getText().toString());

            }
        });

    }

    private void getRestaurentDetails(String catId,String keyWord) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseInsurenceSearch> call = api.searchInsurence("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, catId,keyWord);

        call.enqueue(new Callback<ResponseInsurenceSearch>() {
            @Override
            public void onResponse(Call<ResponseInsurenceSearch> call, Response<ResponseInsurenceSearch> response) {

                Log.e("responseFavRestaurent", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(InsurenceSearchActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        businessList = response.body().getWishListBusiness();

                        adapter = new InsurenceSearchServicesAdapter(businessList, InsurenceSearchActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InsurenceSearchActivity.this);
                        binding.rclrFavouriteRestaurent.setLayoutManager(linearLayoutManager);
                        binding.rclrFavouriteRestaurent.setAdapter(adapter);

                    }

                } else {

                    Toast.makeText(InsurenceSearchActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseInsurenceSearch> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}