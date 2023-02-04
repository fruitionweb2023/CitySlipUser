package com.direct2web.citysipuser.activities.Lawyer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.DoctorAdapter.Account.DoctorFavouriteHospitalAdapter;
import com.direct2web.citysipuser.adapters.LawyerAdapter.LawyerFavouriteListAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorFavouriteHospitalBinding;
import com.direct2web.citysipuser.databinding.ActivityLawyerFavouriteBinding;
import com.direct2web.citysipuser.model.DoctorModels.Account.ResponseDoctorFavouriteHospital;
import com.direct2web.citysipuser.model.LawyerModels.Account.ResponseLawyerFavourite;
import com.direct2web.citysipuser.model.LawyerModels.Account.WishListBusiness;
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

public class LawyerFavouriteActivity extends AppCompatActivity {

    ActivityLawyerFavouriteBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    List<WishListBusiness> businessList1 = new ArrayList<>();
    ProgressDialog pd;
    LawyerFavouriteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_favourite_hospital);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lawyer_favourite);

        sessionManager = new SessionManager(this);
        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);


        binding.bottomnavigation.bbImgMenu.setColorFilter(getResources().getColor(R.color.clr_343347));
        binding.bottomnavigation.bbTxtMenu.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        getRestaurentDetails(sessionManager.getUserId());

        binding.editTextSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());
            }
        });
    }

    private void filter(String text) {

        ArrayList<WishListBusiness> filterList = new ArrayList<>();
        for (WishListBusiness item : businessList1) {
            if (item.getBusinessName().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);

            }
        }
        adapter.filterdeList(filterList);
    }

    private void getRestaurentDetails(String userId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseLawyerFavourite> call = api.getLawyerFavouriteList("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, userId);

        call.enqueue(new Callback<ResponseLawyerFavourite>() {
            @Override
            public void onResponse(Call<ResponseLawyerFavourite> call, Response<ResponseLawyerFavourite> response) {

                Log.e("responseFavRestaurent", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(LawyerFavouriteActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        businessList1 = response.body().getWishListBusiness();

                        adapter = new LawyerFavouriteListAdapter(businessList1, LawyerFavouriteActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LawyerFavouriteActivity.this);
                        binding.rclrFavouriteRestaurent.setLayoutManager(linearLayoutManager);
                        binding.rclrFavouriteRestaurent.setAdapter(adapter);

                    }

                } else {

                    Toast.makeText(LawyerFavouriteActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseLawyerFavourite> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}