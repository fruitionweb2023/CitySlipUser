package com.direct2web.citysipuser.adapters.RestaurentAdapter.RestaurentDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawCouponsListBinding;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails.Offer;

import java.util.List;

public class RestaurentDetailsOfferAdapter extends RecyclerView.Adapter<RestaurentDetailsOfferAdapter.ViewHolder> {

    List<Offer> offerList;
    Context context;

    public RestaurentDetailsOfferAdapter(List<Offer> offerList, Context context) {
        this.offerList = offerList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawCouponsListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_coupons_list, parent, false);
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

        RawCouponsListBinding binding;

        public ViewHolder(@NonNull RawCouponsListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
