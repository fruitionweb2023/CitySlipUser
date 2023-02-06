package com.direct2web.citysipuser.activities.Doctor;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.DoctorAdapter.appointment.AppointmentListAdapter;
import com.direct2web.citysipuser.databinding.ActivityDoctorAppointmentBinding;
import com.direct2web.citysipuser.model.DoctorModels.DoctorAppointment.Order;
import com.direct2web.citysipuser.model.DoctorModels.DoctorAppointment.ResponseDoctorAppointment;
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

public class DoctorAppointmentActivity extends AppCompatActivity {

    ActivityDoctorAppointmentBinding binding;
    SessionManager sessionManager;
    BottomButtonClickListner bottomButtonClickListner;
    ProgressDialog pd;
    AppointmentListAdapter adapter;

    List<Order> orderListDocotor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_appointment);
        sessionManager = new SessionManager(this);

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();


        getOrder(sessionManager.getUserId());

    }

    private void getOrder(String userId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseDoctorAppointment> call = api.getOrder("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, userId);

        call.enqueue(new Callback<ResponseDoctorAppointment>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ResponseDoctorAppointment> call, Response<ResponseDoctorAppointment> response) {

                Log.e("responseDoctorAppointment", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(DoctorAppointmentActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        orderListDocotor = response.body().getOrderList();

                        adapter = new AppointmentListAdapter(orderListDocotor, DoctorAppointmentActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DoctorAppointmentActivity.this);
                        binding.rclrOrder.setLayoutManager(linearLayoutManager);
                        binding.rclrOrder.setAdapter(adapter);


                    }


                } else {

                    Toast.makeText(DoctorAppointmentActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<ResponseDoctorAppointment> call, Throwable t) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });


    }
}