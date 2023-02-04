package com.direct2web.citysipuser.adapters.InsurenceAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.Doctor.DoctorRatingActivity;
import com.direct2web.citysipuser.databinding.RawDoctorRatingListBinding;
import com.direct2web.citysipuser.model.Insurence.ReadMore.Review;

import java.util.List;

public class InsurenceRatingAdapter extends RecyclerView.Adapter<InsurenceRatingAdapter.ViewHolder> {

    List<Review> doctorReview ;
    Context context;


    public InsurenceRatingAdapter(List<Review> doctorReview, Context context) {
        this.doctorReview = doctorReview;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawDoctorRatingListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_doctor_rating_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Review review = doctorReview.get(position);
        holder.binding.txtName.setText(review.getName());
        holder.binding.txtRating.setText(review.getRating());
        holder.binding.txtComment.setText(review.getComment());
        holder.binding.txtDate.setText(review.getSubmitDate());
        holder.binding.txtImgText.setText(String.valueOf(review.getName().charAt(0)));
    }

    @Override
    public int getItemCount() {
        return (doctorReview != null) ? doctorReview.size() : 0;

    }

    public class ViewHolder  extends RecyclerView.ViewHolder {

        RawDoctorRatingListBinding binding;

        public ViewHolder(@NonNull RawDoctorRatingListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }


}
