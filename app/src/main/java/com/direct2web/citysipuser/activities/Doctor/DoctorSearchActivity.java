package com.direct2web.citysipuser.activities.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.DoctorAdapter.Account.DoctorFavouriteHospitalAdapter;
import com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorSearchHospitalAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorSearchBinding;
import com.direct2web.citysipuser.databinding.ActivitySearchBinding;
import com.direct2web.citysipuser.model.DoctorModels.Account.ResponseDoctorFavouriteHospital;
import com.direct2web.citysipuser.model.DoctorModels.ResponseDoctorSearch;
import com.direct2web.citysipuser.model.DoctorModels.WishListBusiness;
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

public class DoctorSearchActivity extends AppCompatActivity {

    ActivityDoctorSearchBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    List<WishListBusiness> businessList = new ArrayList<>();
    ProgressDialog pd;
    DoctorSearchHospitalAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_search);

        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgMyBusiness.setColorFilter(getResources().getColor(R.color.clr_0059C8));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));





        binding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                //filter(editable.toString());
            }
        });

        binding.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRestaurentDetails(sessionManager.getBusinessType(),binding.editText.getText().toString());
               // filter(binding.editText.getText().toString());

            }
        });

    }

    /*private void filter(String text) {

        ArrayList<WishListBusiness> filterList = new ArrayList<>();
        for (WishListBusiness item : businessList) {
            if (item.getBusinessName().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }

        adapter.filterdeList(filterList);
    }*/

    private void getRestaurentDetails(String catId,String keyWord) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseDoctorSearch> call = api.searchHospital("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, catId,keyWord);

        call.enqueue(new Callback<ResponseDoctorSearch>() {
            @Override
            public void onResponse(Call<ResponseDoctorSearch> call, Response<ResponseDoctorSearch> response) {

                Log.e("responseFavRestaurent", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorSearchActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        businessList = response.body().getWishListBusiness();

                        adapter = new DoctorSearchHospitalAdapter(businessList, DoctorSearchActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorSearchActivity.this);
                        binding.rclrFavouriteRestaurent.setLayoutManager(linearLayoutManager);
                        binding.rclrFavouriteRestaurent.setAdapter(adapter);

                    }

                } else {

                    Toast.makeText(DoctorSearchActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorSearch> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}