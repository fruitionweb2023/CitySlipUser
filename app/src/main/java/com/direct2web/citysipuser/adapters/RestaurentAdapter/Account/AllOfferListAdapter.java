package com.direct2web.citysipuser.adapters.RestaurentAdapter.Account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawOfferListBinding;
import com.direct2web.citysipuser.model.RestaurentModels.Account.Offer;

import java.util.List;

public class AllOfferListAdapter   extends RecyclerView.Adapter<AllOfferListAdapter.ViewHolder> {

    List<Offer> offerListItems;
    Context context;

    public AllOfferListAdapter(List<Offer> offerListItems, Context context) {
        this.offerListItems = offerListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawOfferListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_offer_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offer offer = offerListItems.get(position);
        holder.binding.txtOffer.setText(offer.getPercentage() + "%");
        holder.binding.txtCouponsCode.setText(offer.getCoupnCode());
        holder.binding.txtMinAmount.setText(offer.getMinAmount());
        holder.binding.txtMaxAmount.setText(offer.getMaxAmount());
        holder.binding.txtTearmsAndCondition.setText(offer.getTermsCondition());

    }

    @Override
    public int getItemCount() {
        return (offerListItems != null) ? offerListItems.size() : 0;
    }

    public class ViewHolder    extends RecyclerView.ViewHolder {

        RawOfferListBinding binding;

        public ViewHolder(@NonNull RawOfferListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
