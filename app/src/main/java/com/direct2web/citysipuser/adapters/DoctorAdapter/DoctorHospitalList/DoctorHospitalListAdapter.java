package com.direct2web.citysipuser.adapters.DoctorAdapter.DoctorHospitalList;

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
import com.direct2web.citysipuser.databinding.RawDoctorRecommandedListBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalList.Recommended;
import com.direct2web.citysipuser.utils.Api;

import java.util.List;

public class DoctorHospitalListAdapter extends RecyclerView.Adapter<DoctorHospitalListAdapter.ViewHolder> {

    List<Recommended> businessList;
    Context context;
    OnItemClickListner itemClicked;


    public DoctorHospitalListAdapter(List<Recommended> businessList, Context context, OnItemClickListner itemClicked) {
        this.businessList = businessList;
        this.context = context;
        this.itemClicked = itemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawDoctorRecommandedListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_doctor_recommanded_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Recommended restaurent = businessList.get(position);

        holder.binding.txtRestaurentName.setText(restaurent.getBusinessName());
        holder.binding.txtDistance.setText(restaurent.getDistance());
        holder.binding.txtRating.setText(restaurent.getRatingStar());
        holder.binding.txtDistanceTime.setText(restaurent.getDistanceTime());
        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.onItemClicked(position);
            }
        });

        Glide.with(context)
                .load(Api.imageUrl + businessList.get(position).getPhoto())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imgRestaurent);
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RawDoctorRecommandedListBinding binding;

        public ViewHolder(@NonNull RawDoctorRecommandedListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnItemClickListner{
        public void onItemClicked(int postion);

    }
}
