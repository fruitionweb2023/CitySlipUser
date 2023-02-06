package com.direct2web.citysipuser.adapters.DoctorAdapter.appointment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawDoctorAppointmentListBinding;
import com.direct2web.citysipuser.model.DoctorModels.DoctorAppointment.Order;
import com.direct2web.citysipuser.model.DoctorModels.DoctorAppointment.OrderItem;
import com.direct2web.citysipuser.model.DoctorModels.DoctorAppointment.ResponManageStatus;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.viewHolder> {


    List<Order> orderList;
    Context context;
    boolean isAcceptClicked = false;
    boolean isRejectedClicked = false;

    List<OrderItem> orderItemList = new ArrayList<>();

    public AppointmentListAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawDoctorAppointmentListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_doctor_appointment_list, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Order order = orderList.get(position);

        holder.binding.txtOrderId.setText(order.getId());
        holder.binding.txtCustomerName.setText(order.getCustomerName());
        holder.binding.txtDate.setText(order.getOrderDate());
        holder.binding.txtTime.setText(order.getOrderTime());
        holder.binding.txtPrice.setText(order.getTotalAmount());
        holder.binding.txtNumber.setText(order.getAppointmentNumber());
        
        if (order.getStatus().equals("0")) {
            holder.binding.btnAccept.setBackgroundResource(R.drawable.button_box_doctor);
            holder.binding.btnAccept.setText("Accept");
            holder.binding.btnAccept.setTextColor(Color.parseColor("#F8F8F8"));
        } else if (order.getStatus().equals("1")) {
            holder.binding.btnAccept.setBackgroundResource(R.drawable.appointment_button_accept);
            holder.binding.btnAccept.setText("Accepted");
            holder.binding.btnAccept.setTextColor(Color.parseColor("#FF0F9D58"));
            holder.binding.imgDelete.setClickable(false);
            holder.binding.btnAccept.setClickable(false);


        } else if (order.getStatus().equals("2")) {
            holder.binding.btnAccept.setBackgroundResource(R.drawable.appointment_button_regact);
            holder.binding.btnAccept.setText("Rejected");
            holder.binding.btnAccept.setTextColor(Color.parseColor("#FFF44336"));
            holder.binding.btnAccept.setClickable(false);
            holder.binding.imgDelete.setClickable(false);

        }

        holder.binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!isAcceptClicked) {
                   status("order_list",order.getId(),"1");
                   holder.binding.btnAccept.setBackgroundResource(R.drawable.appointment_button_accept);
                   holder.binding.btnAccept.setText("Accepted");
                   holder.binding.btnAccept.setTextColor(Color.parseColor("#FF0F9D58"));
                   isAcceptClicked = true;
                   holder.binding.imgDelete.setClickable(false);
               }
            }
        });

        holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRejectedClicked) {
                    status("order_list",order.getId(),"2");
                    holder.binding.btnAccept.setBackgroundResource(R.drawable.appointment_button_regact);
                    holder.binding.btnAccept.setText("Rejected");
                    holder.binding.btnAccept.setTextColor(Color.parseColor("#FFF44336"));
                    isRejectedClicked = true;
                    holder.binding.btnAccept.setClickable(false);
                }

            }
        });

        orderItemList = order.getOrderItem();

        ServiceItemAdapter serviceItemAdapter = new ServiceItemAdapter(orderItemList,context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        holder.binding.rclrServiceItem.setLayoutManager(manager);
        holder.binding.rclrServiceItem.setAdapter(serviceItemAdapter);

    }

    @Override
    public int getItemCount() {
        return (orderList != null) ? orderList.size() : 0;
    }


    public void updatelist(List<Order> temp) {
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        RawDoctorAppointmentListBinding binding;

        public viewHolder(@NonNull RawDoctorAppointmentListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    private void status(String type, String id,String status) {

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponManageStatus> call = api.getManageStatus("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, type, id,status);
        call.enqueue(new Callback<ResponManageStatus>() {
            @Override
            public void onResponse(Call<ResponManageStatus> call, Response<ResponManageStatus> response) {

                Log.e("responseStatus", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponManageStatus> call, Throwable t) {
                t.printStackTrace();
                Log.e("errorStatus", t.getMessage());
            }
        });

    }
}
