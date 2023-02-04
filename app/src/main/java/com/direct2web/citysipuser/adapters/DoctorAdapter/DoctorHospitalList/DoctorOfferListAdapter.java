package com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHospitalList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorApplyCouponsActivity;
import com.direct2web.citysipuser.databinding.RawCouponsListApplyBinding;
import com.direct2web.citysipuser.databinding.RawDoctorCouponListApplyBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.Offer;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.OfferListItem;

import java.util.List;

public class DoctorOfferListAdapter extends RecyclerView.Adapter<DoctorOfferListAdapter.ViewHolder> {

    List<Offer> offerListItems;
    Context context;
    OnApplyButtonClicked onApplyButtonClicked;

    public DoctorOfferListAdapter(List<Offer> offerListItems, Context context, OnApplyButtonClicked onApplyButtonClicked) {
        this.offerListItems = offerListItems;
        this.context = context;
        this.onApplyButtonClicked = onApplyButtonClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawDoctorCouponListApplyBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_doctor_coupon_list_apply, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Offer offer = offerListItems.get(position);
        holder.binding.txtOffer.setText(offer.getPercentage() + "%");
        holder.binding.txtCouponsCode.setText(offer.getCoupnCode());
        holder.binding.txtMinAmount.setText(offer.getMinAmount());
        holder.binding.txtMaxAmount.setText(offer.getMaxAmount());
        holder.binding.txtTermsAndConditions.setText(offer.getTermsCondition());
        holder.binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onApplyButtonClicked.onApplyClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (offerListItems != null) ? offerListItems.size() : 0;
    }

    public class ViewHolder   extends RecyclerView.ViewHolder {

        RawDoctorCouponListApplyBinding binding;
        public ViewHolder(@NonNull RawDoctorCouponListApplyBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnApplyButtonClicked{
        public void onApplyClicked(int postion);
    }
}
