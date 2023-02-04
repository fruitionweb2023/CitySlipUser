package com.direct2web.citysipuser.adapters.RestaurentAdapter;

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
import com.direct2web.citysipuser.adapters.RestaurentAdapter.BusinessList.BusinessListNearbyAdapter;
import com.direct2web.citysipuser.databinding.RawDoctorNearbyListBinding;
import com.direct2web.citysipuser.databinding.RawMenuListBinding;
import com.direct2web.citysipuser.model.RestaurentModels.WishListBusiness;
import com.direct2web.citysipuser.utils.Api;

import java.util.List;

public class RestaurentSearchHospitalAdapter extends RecyclerView.Adapter<RestaurentSearchHospitalAdapter.ViewHolder> {

    List<WishListBusiness> businessList1;
    Context context;
   OnItemClickListnerNearBy itemClicked;

    public RestaurentSearchHospitalAdapter(List<WishListBusiness> businessList1, Context context,OnItemClickListnerNearBy itemClicked) {
        this.businessList1 = businessList1;
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

        WishListBusiness restaurent = businessList1.get(position);

        holder.binding.txtRestaurentName.setText(restaurent.getBusinessName());
        holder.binding.txtRestaurentDescription.setText(restaurent.getDescription());
        holder.binding.txtDistance.setText(restaurent.getDistance());
        holder.binding.txtRating.setText(restaurent.getRatingStar() + ".0");
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
                .load(Api.imageUrl + businessList1.get(position).getPhoto())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imgRestaurent);

    }

    @Override
    public int getItemCount() {
        return (businessList1 != null) ? businessList1.size() : 0;
    }

   /* public void filterdeList(ArrayList<WishListBusiness> filterList) {
        businessList1 = filterList;
        notifyDataSetChanged();
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder  {
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
