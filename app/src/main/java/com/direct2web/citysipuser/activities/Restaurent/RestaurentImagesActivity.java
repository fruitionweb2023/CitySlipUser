package com.direct2web.citysipuser.activities.Restaurent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.BusinessImagesAndVideos.BusinessImageListAdapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.BusinessImagesAndVideos.BusinessVideoListAdapter;
import com.direct2web.citysipuser.databinding.ActivityRestaurentImagesBinding;
import com.direct2web.citysipuser.model.RestaurentModels.BusignessImagesAndVideos.BusinessImageAndVideo;
import com.direct2web.citysipuser.model.RestaurentModels.BusignessImagesAndVideos.Image;
import com.direct2web.citysipuser.model.RestaurentModels.BusignessImagesAndVideos.Video;
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

public class RestaurentImagesActivity extends AppCompatActivity{

    ActivityRestaurentImagesBinding binding;
    SessionManager sessionManager;
    ProgressDialog pd;
    List<Image> imageList = new ArrayList<>();
    List<Video> videoList = new ArrayList<>();
    BottomButtonClickListner bottomButtonClickListner;
    private String businessId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurent_images);
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
        businessId = getIntent().getStringExtra("businessId");
        getRestaurentDetails(businessId);
    }

    private void getRestaurentDetails(String business) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<BusinessImageAndVideo> call = api.getImagesAndVideos("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, business);

        call.enqueue(new Callback<BusinessImageAndVideo>() {
            @Override
            public void onResponse(Call<BusinessImageAndVideo> call, Response<BusinessImageAndVideo> response) {

                Log.e("responseOrder", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(RestaurentImagesActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        imageList = response.body().getImage();
                        videoList = response.body().getVideo();

                        int numberOfColumns = 3;
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(RestaurentImagesActivity.this,numberOfColumns);
                        binding.rcleImages.setLayoutManager(gridLayoutManager);
                        BusinessImageListAdapter businessImageListAdapter = new BusinessImageListAdapter( imageList,RestaurentImagesActivity.this);
                        binding.rcleImages.setAdapter(businessImageListAdapter);


                        int numberOfColumns1 = 3;
                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(RestaurentImagesActivity.this,numberOfColumns1);
                        binding.rcleVideos.setLayoutManager(gridLayoutManager1);
                        BusinessVideoListAdapter businessVideoListAdapter = new BusinessVideoListAdapter(RestaurentImagesActivity.this, videoList);
                        binding.rcleVideos.setAdapter(businessVideoListAdapter);


                    }

                } else {

                    Toast.makeText(RestaurentImagesActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<BusinessImageAndVideo> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}