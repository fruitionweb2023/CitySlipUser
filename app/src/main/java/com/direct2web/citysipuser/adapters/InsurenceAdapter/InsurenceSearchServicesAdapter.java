package com.direct2web.citysipuser.adapters.InsurenceAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawDoctorNearbyListBinding;
import com.direct2web.citysipuser.databinding.RawInsurenceNearbyListBinding;
import com.direct2web.citysipuser.model.Insurence.Search.WishListBusiness;
import com.direct2web.citysipuser.utils.Api;

import java.util.List;

public class InsurenceSearchServicesAdapter extends RecyclerView.Adapter<InsurenceSearchServicesAdapter.ViewHolder> {

    List<WishListBusiness> businessList1;
    Context context;

    public InsurenceSearchServicesAdapter(List<WishListBusiness> businessList1, Context context) {
        this.businessList1 = businessList1;
        this.context = context;
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

        WishListBusiness restaurent = businessList1.get(position);

        holder.binding.txtRestaurentName.setText(restaurent.getBusinessName());
        holder.binding.txtDistance.setText(restaurent.getDistance());
        holder.binding.txtRating.setText(restaurent.getRatingStar());
        holder.binding.txtDistanceTime.setText(restaurent.getDistanceTime());
        holder.binding.txtRestaurentDescription.setText(restaurent.getDescription());

        Glide.with(context)
                .load(Api.imageUrl + businessList1.get(position).getPhoto())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imgRestaurent);

    }

    @Override
    public int getItemCount() {
        return (businessList1 != null) ? businessList1.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        RawInsurenceNearbyListBinding binding;

        public ViewHolder(@NonNull RawInsurenceNearbyListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
