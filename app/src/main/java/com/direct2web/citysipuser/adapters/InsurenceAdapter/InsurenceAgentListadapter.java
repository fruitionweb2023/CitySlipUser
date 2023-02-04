package com.direct2web.citysipuser.adapters.InsurenceAdapter;

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
import com.direct2web.citysipuser.databinding.RawInsurenceNearbyListBinding;
import com.direct2web.citysipuser.model.Insurence.InsurenceServiceWiseAgent.Agent;
import com.direct2web.citysipuser.utils.Api;

import java.util.List;

public class InsurenceAgentListadapter extends RecyclerView.Adapter<InsurenceAgentListadapter.ViewHolder>{

    List<Agent> businessListNearBy;
    Context context;
    OnItemClickListnerNearBy itemClicked;


    public InsurenceAgentListadapter(List<Agent> businessListNearBy, Context context, OnItemClickListnerNearBy itemClicked) {
        this.businessListNearBy = businessListNearBy;
        this.context = context;
        this.itemClicked = itemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawInsurenceNearbyListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_insurence_nearby_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Agent restaurent = businessListNearBy.get(position);

        holder.binding.txtRestaurentName.setText(restaurent.getName());
        holder.binding.txtRestaurentDescription.setText(restaurent.getInformation());
        holder.binding.txtDistance.setText(restaurent.getDistance());
        holder.binding.txtRating.setText(restaurent.getRatingStar());
        holder.binding.txtDistanceTime.setText(restaurent.getDistanceTime());

        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.onNearByItemClicked(position);
            }
        });

        Glide.with(context)
                .load(Api.imageUrl + businessListNearBy.get(position).getProfile())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imgRestaurent);
    }

    @Override
    public int getItemCount() {

        return (businessListNearBy != null) ? businessListNearBy.size() : 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RawInsurenceNearbyListBinding binding;

        public ViewHolder(@NonNull RawInsurenceNearbyListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnItemClickListnerNearBy{
        public void onNearByItemClicked(int postion);
    }
}
