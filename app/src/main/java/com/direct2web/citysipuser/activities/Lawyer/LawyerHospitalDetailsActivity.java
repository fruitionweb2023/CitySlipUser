package com.direct2web.citysipuser.activities.Lawyer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorHospitalReadMoreActivity;
import com.direct2web.citysipuser.adapters.LawyerAdapter.LawyerDetailsAddAdapter;
import com.direct2web.citysipuser.adapters.LawyerAdapter.LawyerDetailsOfferAdapter;
import com.direct2web.citysipuser.adapters.LawyerAdapter.LawyerDetailsTimeAdapter;
import com.direct2web.citysipuser.databinding.ActivityLawyerHospitalDetailsBinding;
import com.direct2web.citysipuser.databinding.BookAppointmentBottomsheetBinding;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.Offer;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.ResponseLawyerDetails;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.ResponseLawyerWishList;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.ResponselawyerAddToCart;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.ResponselawyerTime;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.Service;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.Slot;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LawyerHospitalDetailsActivity extends AppCompatActivity implements LawyerDetailsAddAdapter.OnAddButtonClicked, LawyerDetailsTimeAdapter.OnItemClickListner {

    ActivityLawyerHospitalDetailsBinding binding;
    SessionManager sessionManager;
    ProgressDialog pd;
    BottomButtonClickListner bottomButtonClickListner;
    LawyerDetailsOfferAdapter adapter;
    List<Offer> offerList;
    String businessId = "";
    LawyerDetailsAddAdapter lawyerDetailsAddAdapter;
    List<Service> serviceList;
    List<Slot> slotList;
    String time = "";
    RecyclerView recyclerView;
    DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lawyer_hospital_details);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_343347));
        binding.bottomnavigation.bbTxtHome.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        businessId = getIntent().getStringExtra("business_id");

        if (sessionManager.getUserId().equals("")) {
            Toast.makeText(LawyerHospitalDetailsActivity.this, "Please Contact Administrator", Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else {
            getRestaurentDetails(businessId, sessionManager.getUserId());
        }

        binding.txtReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LawyerHospitalDetailsActivity.this, LawyerReadMoreActivity.class);
                i.putExtra("business_id", businessId);
                startActivity(i);
            }
        });

        binding.btnFavOrNot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.btnFavOrNot.setChecked(true);
                    sendStatus(businessId,sessionManager.getUserId(),"1");
                } else {
                    binding.btnFavOrNot.setChecked(false);
                    sendStatus(businessId,sessionManager.getUserId(),"0");
                }
            }
        });

        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Link...");
                sendIntent.putExtra(Intent.EXTRA_TITLE, binding.txtRestaurentName.getText().toString());

                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });


    }


    private void getRestaurentDetails(String businessId, String userId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseLawyerDetails> call = api.getLawyerDetails("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, businessId, "", "", userId);

        call.enqueue(new Callback<ResponseLawyerDetails>() {
            @Override
            public void onResponse(Call<ResponseLawyerDetails> call, Response<ResponseLawyerDetails> response) {

                Log.e("responseOrder", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(LawyerHospitalDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        binding.txtRestaurentName.setText(response.body().getBusinessName());
                        binding.txtRestaurentDescription.setText(response.body().getDescription());
                        binding.txtRating.setText(response.body().getRatingStar());
                        binding.txtDistanceTime.setText(response.body().getDistanceTime());
                        binding.txtDistance.setText(response.body().getDistance());

                        offerList = response.body().getOffer();
                        adapter = new LawyerDetailsOfferAdapter(offerList, LawyerHospitalDetailsActivity.this);
                        binding.rcleOffer.setAdapter(adapter);

                        serviceList = response.body().getServiceList();

                        lawyerDetailsAddAdapter = new LawyerDetailsAddAdapter(serviceList, LawyerHospitalDetailsActivity.this, LawyerHospitalDetailsActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LawyerHospitalDetailsActivity.this);
                        binding.rclrRecommanded.setLayoutManager(linearLayoutManager);
                        binding.rclrRecommanded.setAdapter(lawyerDetailsAddAdapter);


                        if (response.body().getWishList().equals("1")) {
                            binding.btnFavOrNot.setChecked(true);
                        } else {
                            binding.btnFavOrNot.setChecked(false);
                        }

                    }

                } else {

                    Toast.makeText(LawyerHospitalDetailsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseLawyerDetails> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    public void sendStatus(String businessId,String userId,String status){

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponseLawyerWishList> call = api.getLawyerStatus("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, businessId,userId,status);
        call.enqueue(new Callback<ResponseLawyerWishList>() {
            @Override
            public void onResponse(Call<ResponseLawyerWishList> call, Response<ResponseLawyerWishList> response) {

                Log.e("responseWishList", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(LawyerHospitalDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(LawyerHospitalDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseLawyerWishList> call, Throwable t) {
                t.printStackTrace();
                Log.e("errorDelete", t.getMessage());
            }
        });
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onButtonClicked(int postion) {

        Service recommendedItem = serviceList.get(postion);

         BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        LayoutInflater layoutInflater = LayoutInflater.from(LawyerHospitalDetailsActivity.this);
        BookAppointmentBottomsheetBinding binding2 = DataBindingUtil.inflate(layoutInflater, R.layout.book_appointment_bottomsheet, null, false);
        bottomSheetDialog.setContentView(binding2.getRoot());
        binding2.imgDishLogo.setImageResource(R.drawable.city_sip_logo);
        bottomSheetDialog.show();

        binding2.txtRestaurentName.setText(recommendedItem.getDoctorName());
        binding2.txtServiceName.setText(recommendedItem.getServiceName());
        binding2.txtRestaurentDescription.setText(recommendedItem.getDescription());
        recyclerView = bottomSheetDialog.findViewById(R.id.rclr_time);

        binding2.txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                // date picker dialog
                picker = new DatePickerDialog(LawyerHospitalDetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                binding2.txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" +year);
                            }
                        }, year, month, day);
                picker.show();
            }
            });


                binding2.txtDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        binding2.btnBookAppointment.setTextColor(getResources().getColor(R.color.white));
                        binding2.btnBookAppointment.setBackground(getResources().getDrawable(R.drawable.doctor_button));
                        getTime(binding2.txtDate.getText().toString());
                    }
                });

        Glide.with(LawyerHospitalDetailsActivity.this)
                .load( recommendedItem.getImage())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding2.imgDishLogo);

        binding2.btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAddToCart(sessionManager.getUserId(),recommendedItem.getId(),binding2.txtDate.getText().toString(),time);
                bottomSheetDialog.hide();
            }
        });

    }

    private void sendAddToCart(String userId,String itemId,String date,String time) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponselawyerAddToCart> call = api.sendLawyerAddToCart("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,userId,itemId,date,time);

        call.enqueue(new Callback<ResponselawyerAddToCart>() {
            @Override
            public void onResponse(Call<ResponselawyerAddToCart> call, Response<ResponselawyerAddToCart> response) {

                Log.e("responseDoctorAddtoCart", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(LawyerHospitalDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(LawyerHospitalDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(LawyerHospitalDetailsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponselawyerAddToCart> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void getTime(String appointmentDate) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponselawyerTime> call = api.getLawyerTime("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,appointmentDate);

        call.enqueue(new Callback<ResponselawyerTime>() {
            @Override
            public void onResponse(Call<ResponselawyerTime> call, Response<ResponselawyerTime> response) {

                Log.e("responseDoctorTime", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(LawyerHospitalDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        slotList = response.body().getSlotList();

                        int numberOfColumns = 2;
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(LawyerHospitalDetailsActivity.this, numberOfColumns);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        LawyerDetailsTimeAdapter deleteImageDoctorAdapter = new LawyerDetailsTimeAdapter(slotList, LawyerHospitalDetailsActivity.this, LawyerHospitalDetailsActivity.this);
                        recyclerView.setAdapter(deleteImageDoctorAdapter);
                    }

                } else {

                    Toast.makeText(LawyerHospitalDetailsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponselawyerTime> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onClicked(int postion) {
        time = slotList.get(postion).getSlot();
    }


}