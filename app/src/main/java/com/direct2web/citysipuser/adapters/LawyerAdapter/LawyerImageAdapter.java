package com.direct2web.citysipuser.adapters.LawyerAdapter;

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
import com.direct2web.citysipuser.databinding.RawImageItemBinding;
import com.direct2web.citysipuser.model.LawyerModels.ReadMore.Image;
import com.direct2web.citysipuser.utils.Api;

import java.util.List;

public class LawyerImageAdapter extends RecyclerView.Adapter<LawyerImageAdapter.ViewHolder> {

    List<Image> imageList;
    Context context;
    OnImageClickedListner onImageClickedListner;

    public LawyerImageAdapter(List<Image> imageList,Context context, OnImageClickedListner onImageClickedListner) {
        this.imageList = imageList;
        this.context = context;
        this.onImageClickedListner = onImageClickedListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawImageItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_image_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context)
                .load(Api.imageUrl + imageList.get(position).getPhoto())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imgRestaurent);

        holder.binding.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClickedListner.onImageClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (imageList != null) ? imageList.size() : 0;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder  {

        RawImageItemBinding binding;

        public ViewHolder(@NonNull RawImageItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnImageClickedListner{
        public void onImageClicked(int postion);
    }
}
