package com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHospitalDetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawDoctorServicesItemBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalDetails.Service;

import java.util.List;

public class HospitalDetailsAddServiceAdapter extends RecyclerView.Adapter<HospitalDetailsAddServiceAdapter.ViewHolder>{

    List<Service> itemList;
    Context context;
    OnAddButtonClicked buttonClicked;

    public HospitalDetailsAddServiceAdapter(List<Service> itemList, Context context, OnAddButtonClicked buttonClicked) {
        this.itemList = itemList;
        this.context = context;
        this.buttonClicked = buttonClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawDoctorServicesItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_doctor_services_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Service items = itemList.get(position);
        holder.binding.txtDishDescription.setText(items.getDescription());
        holder.binding.txtPrice.setText(items.getAmount());
        holder.binding.txtDoctorName.setText(items.getDoctorName());
        holder.binding.txtDoctorService.setText(items.getServiceName());

        Glide.with(context)
                .load( itemList.get(position).getImage())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imgDishLogo);

        holder.binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked.onButtonClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (itemList != null) ? itemList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RawDoctorServicesItemBinding binding;

        public ViewHolder(@NonNull RawDoctorServicesItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnAddButtonClicked{
        public void onButtonClicked(int postion);
    }

}
