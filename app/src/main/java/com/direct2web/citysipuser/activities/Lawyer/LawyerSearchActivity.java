package com.direct2web.citysipuser.activities.Lawyer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.LawyerAdapter.LawyerSearchHospitalAdapter;
import com.direct2web.citysipuser.databinding.ActivityLawyerSearchBinding;
import com.direct2web.citysipuser.model.LawyerModels.ResponseLawyerSearch;
import com.direct2web.citysipuser.model.LawyerModels.WishListBusiness;
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

public class LawyerSearchActivity extends AppCompatActivity {

    ActivityLawyerSearchBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    LawyerSearchHospitalAdapter lawyerSearchHospitalAdapter;
    List<WishListBusiness> searchListlawyer = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lawyer_search);

        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgMyBusiness.setColorFilter(getResources().getColor(R.color.clr_343347));

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

        Call<ResponseLawyerSearch> call = api.searchLawyer("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, catId,keyWord);

        call.enqueue(new Callback<ResponseLawyerSearch>() {
            @Override
            public void onResponse(Call<ResponseLawyerSearch> call, Response<ResponseLawyerSearch> response) {

                Log.e("responseFavRestaurent", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(LawyerSearchActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        searchListlawyer = response.body().getWishListBusiness();
                        lawyerSearchHospitalAdapter = new LawyerSearchHospitalAdapter(searchListlawyer, LawyerSearchActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LawyerSearchActivity.this);
                        binding.lawyerSearch.setLayoutManager(linearLayoutManager);
                        binding.lawyerSearch.setAdapter(lawyerSearchHospitalAdapter);

                    }

                } else {

                    Toast.makeText(LawyerSearchActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseLawyerSearch> call, Throwable t) {

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}