package com.direct2web.citysipuser.activities.Insurance;

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
import com.direct2web.citysipuser.adapters.DoctorAdapter.HospitalDetailsTimeAdapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceAddServiceAdapter;
import com.direct2web.citysipuser.adapters.InsurenceAdapter.InsurenceDetailsTimeListAdapter;
import com.direct2web.citysipuser.databinding.ActivityInsurenceDetailsBinding;
import com.direct2web.citysipuser.databinding.InsurenceBookAppointmentBottomsheetBinding;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.ResponseInsurenceAddToCart;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.ResponseInsurenceAgentDetails;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.ResponseInsurenceGetTimeASlot;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.ResponseInsurenceWishList;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.Service;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.Slot;
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

public class InsurenceDetailsActivity extends AppCompatActivity implements InsurenceAddServiceAdapter.OnAddButtonClicked, HospitalDetailsTimeAdapter.OnItemClickListner, InsurenceDetailsTimeListAdapter.OnItemClickListner {

    ActivityInsurenceDetailsBinding binding;
    SessionManager sessionManager;
    ProgressDialog pd;
    BottomButtonClickListner bottomButtonClickListner;
    String getAgentId = "";
    InsurenceAddServiceAdapter insurenceAddServiceAdapter;
    List<Service> serviceList;
    List<Slot> slotList;
    String time = "";
    RecyclerView recyclerView;
    DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insurence_details);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_0059C8));
        binding.bottomnavigation.bbTxtHome.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        getAgentId = getIntent().getStringExtra("agent_id");

        if (sessionManager.getUserId().equals("")) {
            Toast.makeText(InsurenceDetailsActivity.this, "Please Contact Administrator", Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else {
            getRestaurentDetails(getAgentId);
        }

        binding.txtReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InsurenceDetailsActivity.this, InsurenceReadMoreActivity.class);
                i.putExtra("agent_id_details", getAgentId);
                startActivity(i);
            }
        });

        binding.btnFavOrNot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.btnFavOrNot.setChecked(true);
                    sendStatus(getAgentId,sessionManager.getUserId(),"1");
                } else {
                    binding.btnFavOrNot.setChecked(false);
                    sendStatus(getAgentId,sessionManager.getUserId(),"0");
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


    private void getRestaurentDetails(String agentId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseInsurenceAgentDetails> call = api.getAgentDetails("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, agentId);

        call.enqueue(new Callback<ResponseInsurenceAgentDetails>() {
            @Override
            public void onResponse(Call<ResponseInsurenceAgentDetails> call, Response<ResponseInsurenceAgentDetails> response) {

                Log.e("responseInsOrder", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(InsurenceDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        binding.txtRestaurentName.setText(response.body().getName());
                        binding.txtRestaurentDescription.setText(response.body().getInformation());
                        binding.txtRating.setText(response.body().getRatingStar());
                        binding.txtDistanceTime.setText(response.body().getDistanceTime());
                        binding.txtDistance.setText(response.body().getDistance());


                        serviceList = response.body().getServiceList();

                        insurenceAddServiceAdapter = new InsurenceAddServiceAdapter(serviceList, InsurenceDetailsActivity.this, InsurenceDetailsActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InsurenceDetailsActivity.this);
                        binding.rclrRecommanded.setLayoutManager(linearLayoutManager);
                        binding.rclrRecommanded.setAdapter(insurenceAddServiceAdapter);


                        if (response.body().getWishList().equals("1")) {
                            binding.btnFavOrNot.setChecked(true);
                        } else {
                            binding.btnFavOrNot.setChecked(false);
                        }

                    }

                } else {

                    Toast.makeText(InsurenceDetailsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseInsurenceAgentDetails> call, Throwable t) {

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
        Call<ResponseInsurenceWishList> call = api.getInsurenceStatus("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, businessId,userId,status);
        call.enqueue(new Callback<ResponseInsurenceWishList>() {
            @Override
            public void onResponse(Call<ResponseInsurenceWishList> call, Response<ResponseInsurenceWishList> response) {

                Log.e("responseInsWishList", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(InsurenceDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(InsurenceDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseInsurenceWishList> call, Throwable t) {
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

        LayoutInflater layoutInflater = LayoutInflater.from(InsurenceDetailsActivity.this);
        InsurenceBookAppointmentBottomsheetBinding binding2 = DataBindingUtil.inflate(layoutInflater, R.layout.insurence_book_appointment_bottomsheet, null, false);
        bottomSheetDialog.setContentView(binding2.getRoot());
        binding2.imgDishLogo.setImageResource(R.drawable.city_sip_logo);
        bottomSheetDialog.show();

        binding2.txtRestaurentName.setText(recommendedItem.getName());
       // binding2.txtRestaurentDescription.setText(recommendedItem.getDescription());
        recyclerView = bottomSheetDialog.findViewById(R.id.rclr_time);

        binding2.txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                // date picker dialog
                picker = new DatePickerDialog(InsurenceDetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                binding2.txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
                        binding2.btnBookAppointment.setBackground(getResources().getDrawable(R.drawable.insurence_button));
                        getTime(binding2.txtDate.getText().toString());
                    }
                });

        Glide.with(InsurenceDetailsActivity.this)
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

        Call<ResponseInsurenceAddToCart> call = api.sendInsurenceAddToCart("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,userId,itemId,date,time);

        call.enqueue(new Callback<ResponseInsurenceAddToCart>() {
            @Override
            public void onResponse(Call<ResponseInsurenceAddToCart> call, Response<ResponseInsurenceAddToCart> response) {

                Log.e("responseInsurencetoCart", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(InsurenceDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        Intent intent = new Intent(InsurenceDetailsActivity.this, InsurenceAppointmentBookedActivity.class);
                        startActivity(intent);
                       // Toast.makeText(InsurenceDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(InsurenceDetailsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsurenceAddToCart> call, Throwable t) {

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

        Call<ResponseInsurenceGetTimeASlot> call = api.getInsurenceTimeSloat("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,appointmentDate);

        call.enqueue(new Callback<ResponseInsurenceGetTimeASlot>() {
            @Override
            public void onResponse(Call<ResponseInsurenceGetTimeASlot> call, Response<ResponseInsurenceGetTimeASlot> response) {

                Log.e("responseInsurenceTime", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(InsurenceDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        slotList = response.body().getSlotList();

                        int numberOfColumns = 2;
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(InsurenceDetailsActivity.this, numberOfColumns);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        InsurenceDetailsTimeListAdapter insurenceDetailsTimeListAdapter = new InsurenceDetailsTimeListAdapter(slotList, InsurenceDetailsActivity.this, InsurenceDetailsActivity.this);
                        recyclerView.setAdapter(insurenceDetailsTimeListAdapter);
                    }

                } else {

                    Toast.makeText(InsurenceDetailsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseInsurenceGetTimeASlot> call, Throwable t) {

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