package com.direct2web.citysipuser.adapters.LawyerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawImageItemBinding;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.Image;
import com.direct2web.citysipuser.utils.Api;

import java.util.List;

public class LawyerImageListAdapter extends RecyclerView.Adapter<LawyerImageListAdapter.ViewHolder>{

    List<Image> imageList;
    Context context;


    public LawyerImageListAdapter(List<Image> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawImageItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_image_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load(Api.imageUrl + imageList.get(position).getPhoto())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imgRestaurent);
    }

    @Override
    public int getItemCount() {
        return (imageList != null) ? imageList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RawImageItemBinding binding;
        public ViewHolder(@NonNull RawImageItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
