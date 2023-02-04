package com.direct2web.citysipuser.adapters.RestaurentAdapter.BusinessRating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawRatingListBinding;
import com.direct2web.citysipuser.model.RestaurentModels.RestaurentRating.Review;

import java.util.List;

public class BusinessRatingAdapter extends RecyclerView.Adapter<BusinessRatingAdapter.ViewHolder> {

    List<Review> reviewList;
    Context context;


    public BusinessRatingAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawRatingListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_rating_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Review review = reviewList.get(position);
        holder.binding.txtName.setText(review.getName());
        holder.binding.txtRating.setText(review.getRating());
        holder.binding.txtComment.setText(review.getComment());
        holder.binding.txtDate.setText(review.getSubmitDate());
        holder.binding.txtImgText.setText(String.valueOf(review.getName().charAt(0)));
    }

    @Override
    public int getItemCount() {
        return (reviewList != null) ? reviewList.size() : 0;

    }

    public class ViewHolder  extends RecyclerView.ViewHolder {

        RawRatingListBinding binding;

        public ViewHolder(@NonNull RawRatingListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }


}
