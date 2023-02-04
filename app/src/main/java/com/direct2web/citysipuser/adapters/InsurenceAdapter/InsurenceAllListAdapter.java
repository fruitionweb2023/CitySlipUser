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
import com.direct2web.citysipuser.databinding.RawInsurenceAllRecommandedListBinding;
import com.direct2web.citysipuser.databinding.RawInsurenceRecommandedListBinding;
import com.direct2web.citysipuser.model.Insurence.InsurenceList.Service;
import com.direct2web.citysipuser.utils.Api;

import java.util.List;

public class InsurenceAllListAdapter extends RecyclerView.Adapter<InsurenceAllListAdapter.ViewHolder> {

    List<Service> businessList;
    Context context;
    OnItemClickListner itemClicked;


    public InsurenceAllListAdapter(List<Service> businessList, Context context, OnItemClickListner itemClicked) {
        this.businessList = businessList;
        this.context = context;
        this.itemClicked = itemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawInsurenceAllRecommandedListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_insurence_all_recommanded_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Service restaurent = businessList.get(position);

        holder.binding.txtRestaurentName.setText(restaurent.getName());
        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.onItemClicked(position);
            }
        });

        Glide.with(context)
                .load(Api.imageUrl + businessList.get(position).getImage())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imgRestaurent);
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RawInsurenceAllRecommandedListBinding binding;

        public ViewHolder(@NonNull RawInsurenceAllRecommandedListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnItemClickListner{
        public void onItemClicked(int postion);

    }
}
