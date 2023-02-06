package com.direct2web.citysipuser.adapters.DoctorAdapter.appointment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawDoctorServiceItemBinding;
import com.direct2web.citysipuser.model.DoctorModels.DoctorAppointment.OrderItem;
import java.util.ArrayList;
import java.util.List;

public class ServiceItemAdapter extends RecyclerView.Adapter<ServiceItemAdapter.viewHolder> {


    List<OrderItem> orderList;
    Context context;

    List<OrderItem> orderItemList = new ArrayList<>();

    public ServiceItemAdapter(List<OrderItem> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawDoctorServiceItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_doctor_service_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        OrderItem order = orderList.get(position);

        holder.binding.txtName.setText(order.getServiceName());
        holder.binding.txtQnty.setText(order.getQty());
        holder.binding.txtAmount.setText(order.getAmount());
        holder.binding.txtAppointmentDate.setText(order.getAppointmentDate());
        holder.binding.txtAppointmentTime.setText(order.getAppointmentTime());

    }

    @Override
    public int getItemCount() {
        return (orderList != null) ? orderList.size() : 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        RawDoctorServiceItemBinding binding;

        public viewHolder(@NonNull RawDoctorServiceItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
