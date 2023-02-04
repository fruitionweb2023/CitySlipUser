package com.direct2web.citysipuser.adapters.RestaurentAdapter.RestaurentDetails;

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
import com.direct2web.citysipuser.databinding.RawOrderItemBinding;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails.RecommendedItem;

import java.util.List;

public class RestaurentAddItemAdapter extends RecyclerView.Adapter<RestaurentAddItemAdapter.ViewHolder>{

    List<RecommendedItem> itemList;
    Context context;
    OnAddButtonClicked buttonClicked;

    public RestaurentAddItemAdapter(List<RecommendedItem> itemList, Context context,OnAddButtonClicked buttonClicked) {
        this.itemList = itemList;
        this.context = context;
        this.buttonClicked = buttonClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawOrderItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_order_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RecommendedItem items = itemList.get(position);
        holder.binding.txtOffer.setText(items.getOfferPersantage() + "% off");
        holder.binding.txtDishName.setText(items.getTitle());
        holder.binding.txtDishDescription.setText(items.getDescription());
        holder.binding.txtDiscount.setText(items.getOfferPersantage());
        holder.binding.txtMaxAmount.setText(items.getOfferMaxAmount());
        holder.binding.txtPrice.setText(items.getAmount());

        Glide.with(context)
                .load( itemList.get(position).getImage())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imgDishLogo);

        if (items.getCategory().equals("Veg")){

            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.ic_small_veg))
                    .error(R.drawable.city_sip_logo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.binding.imgVegNonveg);
        } else {
            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.ic_non_veg))
                    .error(R.drawable.city_sip_logo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.binding.imgVegNonveg);

        }
        holder.binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked.onButtonClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (itemList != null) ? itemList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RawOrderItemBinding binding;

        public ViewHolder(@NonNull RawOrderItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnAddButtonClicked{
        public void onButtonClicked(int postion);
    }

}
