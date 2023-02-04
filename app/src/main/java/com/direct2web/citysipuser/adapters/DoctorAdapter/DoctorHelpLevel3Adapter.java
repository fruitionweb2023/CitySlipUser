package com.direct2web.citysipuser.adapters.DoctorAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawCustomerCareDetailsBinding;
import com.direct2web.citysipuser.model.DoctorModels.HelpAndFaqs.Leval3.Level3;

import java.util.List;

public class DoctorHelpLevel3Adapter extends RecyclerView.Adapter<DoctorHelpLevel3Adapter.ViewHolder> {

    List<Level3> addressListItem;
    Context context;

    public DoctorHelpLevel3Adapter(List<Level3> addressListItem, Context context) {
        this.addressListItem = addressListItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawCustomerCareDetailsBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_customer_care_details, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Level3 addressItem = addressListItem.get(position);
        holder.binding.txtTitle.setText(addressItem.getTitle());
        holder.binding.txtDescreption.setText(addressItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return (addressListItem != null) ? addressListItem.size() : 0;
    }

    public class ViewHolder   extends RecyclerView.ViewHolder  {

        RawCustomerCareDetailsBinding binding;

        public ViewHolder(@NonNull RawCustomerCareDetailsBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
