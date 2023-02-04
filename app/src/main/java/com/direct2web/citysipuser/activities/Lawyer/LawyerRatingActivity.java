package com.direct2web.citysipuser.activities.Lawyer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.LawyerAdapter.LawyerRatingAdapter;
import com.direct2web.citysipuser.databinding.ActivityLawyerRatingBinding;
import com.direct2web.citysipuser.model.LawyerModels.ReadMore.LawyerReview;
import com.direct2web.citysipuser.model.LawyerModels.ReadMore.Review;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.gson.Gson;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LawyerRatingActivity extends AppCompatActivity {

    ActivityLawyerRatingBinding binding;
    SessionManager sessionManager;
    ProgressDialog pd;
    String businessId = "";
    BottomButtonClickListner bottomButtonClickListner;
    LawyerRatingAdapter doctorRatingAdapter;
    List<Review> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lawyer_rating);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_343347));
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
    }

    private void getRestaurentDetails(String businessId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<LawyerReview> call = api.getLawyerreview("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, businessId);

        call.enqueue(new Callback<LawyerReview>() {
            @Override
            public void onResponse(Call<LawyerReview> call, Response<LawyerReview> response) {

                Log.e("responseReview", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(LawyerRatingActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        binding.txtOverallRating.setText(response.body().getOverallRatings());
                        binding.txtTotelRating.setText(response.body().getTotalRating());

                        reviewList = response.body().getReviews();
                        doctorRatingAdapter = new LawyerRatingAdapter(reviewList, LawyerRatingActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LawyerRatingActivity.this);
                        binding.rcleRating.setLayoutManager(linearLayoutManager);
                        binding.rcleRating.setAdapter(doctorRatingAdapter);
                    }

                } else {

                    Toast.makeText(LawyerRatingActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LawyerReview> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}