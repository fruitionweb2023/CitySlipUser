package com.direct2web.citysipuser.adapters.DoctorAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.model.DoctorModels.Slider;
import com.direct2web.citysipuser.utils.Api;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
    List<Slider> mSliderItems;
    OnItemClickListner itemClicked;

    // Constructor
    public SliderAdapter(Context context, List<Slider> sliderDataArrayList, OnItemClickListner itemClicked) {
        this.mSliderItems = sliderDataArrayList;
        this.itemClicked = itemClicked;
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        final Slider sliderItem = mSliderItems.get(position);

        Glide.with(viewHolder.itemView)
                .load(Api.imageUrl + sliderItem.getImage())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(view -> itemClicked.onClicked(position));
    }
    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;
        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myImage);
            this.itemView = itemView;

        }
    }

    public void updateList(List<Slider> sliderList){
        this.mSliderItems = sliderList;
        notifyDataSetChanged();

    }

    public interface OnItemClickListner{
        public void onClicked(int postion);
    }
}