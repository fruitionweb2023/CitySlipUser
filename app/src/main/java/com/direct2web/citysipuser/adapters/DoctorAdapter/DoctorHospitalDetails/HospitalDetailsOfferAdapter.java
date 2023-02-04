package com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHospitalDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawDoctorCouponListBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalDetails.Offer;


import java.util.List;

public class HospitalDetailsOfferAdapter extends RecyclerView.Adapter<HospitalDetailsOfferAdapter.ViewHolder> {

    List<Offer> offerList;
    Context context;

    public HospitalDetailsOfferAdapter(List<Offer> offerList, Context context) {
        this.offerList = offerList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawDoctorCouponListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_doctor_coupon_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Offer offers = offerList.get(position);
        holder.binding.txtOffer.setText(offers.getPercentage() + "%");

    }

    @Override
    public int getItemCount() {
        return (offerList != null) ? offerList.size() : 0;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder  {

        RawDoctorCouponListBinding binding;

        public ViewHolder(@NonNull RawDoctorCouponListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
