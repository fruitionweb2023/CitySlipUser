package com.direct2web.citysipuser.adapters.RestaurentAdapter.BusinessList;

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
import com.direct2web.citysipuser.databinding.RawMenuListBinding;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessList.NearByList;
import com.direct2web.citysipuser.utils.Api;

import java.util.List;

public class BusinessListNearbyAdapter  extends RecyclerView.Adapter<BusinessListNearbyAdapter.ViewHolder>{

    List<NearByList> businessListNearBy;
    Context context;
    OnItemClickListnerNearBy itemClicked;


    public BusinessListNearbyAdapter(List<NearByList> businessListNearBy, Context context, OnItemClickListnerNearBy itemClicked) {
        this.businessListNearBy = businessListNearBy;
        this.context = context;
        this.itemClicked = itemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawMenuListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_menu_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        NearByList restaurent = businessListNearBy.get(position);

        holder.binding.txtRestaurentName.setText(restaurent.getBusinessName());
        holder.binding.txtRestaurentDescription.setText(restaurent.getDescription());
        holder.binding.txtDistance.setText(restaurent.getDistance());
        holder.binding.txtRating.setText(restaurent.getRatingStar());
        holder.binding.txtOffer.setText(restaurent.getOfferPer() + "% off");
        holder.binding.txtDistanceTime.setText(restaurent.getDistanceTime());
        holder.binding.txtDiscount.setText(restaurent.getOfferPer());
        holder.binding.txtMaxOffer.setText(restaurent.getOfferMax());

        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.onNearByItemClicked(position);
            }
        });

        Glide.with(context)
                .load(Api.imageUrl + businessListNearBy.get(position).getPhoto())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imgRestaurent);
    }

    @Override
    public int getItemCount() {

        return (businessListNearBy != null) ? businessListNearBy.size() : 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RawMenuListBinding binding;

        public ViewHolder(@NonNull RawMenuListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnItemClickListnerNearBy{
        public void onNearByItemClicked(int postion);
    }
}
