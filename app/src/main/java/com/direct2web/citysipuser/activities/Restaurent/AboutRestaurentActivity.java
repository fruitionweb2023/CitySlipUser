package com.direct2web.citysipuser.activities.Restaurent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.AboutRestaurentImage.AboutRestaurentImageAdapter;
import com.direct2web.citysipuser.databinding.ActivityAboutRestaurentBinding;
import com.direct2web.citysipuser.model.RestaurentModels.ReadMoreDetails.Image;
import com.direct2web.citysipuser.model.RestaurentModels.ReadMoreDetails.ReadMoreDetails;
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

public class AboutRestaurentActivity extends AppCompatActivity implements AboutRestaurentImageAdapter.OnImageClickedListner {

    ActivityAboutRestaurentBinding binding;
    SessionManager sessionManager;
    List<Image> imageList = new ArrayList<>();
    ProgressDialog pd;
    AboutRestaurentImageAdapter adapter;
    String businessId="";
    BottomButtonClickListner bottomButtonClickListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_restaurent);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);
        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_f54748));
        binding.bottomnavigation.bbTxtHome.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        businessId = getIntent().getStringExtra("business_id");

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        getRestaurentDetails(businessId);

        binding.btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutRestaurentActivity.this,RestaurentRatingActivity.class);
                intent.putExtra("business_id",businessId);
                startActivity(intent);
            }
        });


    }

    private void getRestaurentDetails(String businessId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ReadMoreDetails> call = api.getReadMoreDetails("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, businessId);

        call.enqueue(new Callback<ReadMoreDetails>() {
            @Override
            public void onResponse(Call<ReadMoreDetails> call, Response<ReadMoreDetails> response) {

                Log.e("responseOrder", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(AboutRestaurentActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        binding.txtRestaurentName.setText(response.body().getBusinessName());
                        binding.txtRestaurentDescription.setText(response.body().getDescription());
                        binding.txtRating.setText(response.body().getRatingStar());
                        binding.txtFeatures.setText(response.body().getFeatures());
                        binding.txtLocation.setText(response.body().getLocation());
                        binding.txtMobileNumber.setText(response.body().getContact());
                        binding.txtWebSite.setText(response.body().getWebsite());
                        binding.txtTips.setText(response.body().getTips());

                        imageList = response.body().getImages();
                        adapter = new AboutRestaurentImageAdapter(imageList, AboutRestaurentActivity.this, AboutRestaurentActivity.this);
                        binding.rcleAboutImages.setAdapter(adapter);


                    }

                } else {

                    Toast.makeText(AboutRestaurentActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ReadMoreDetails> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onImageClicked(int postion) {
        Intent intent = new Intent(AboutRestaurentActivity.this,RestaurentImagesActivity.class);
        intent.putExtra("businessId",businessId);
        startActivity(intent);
    }
}