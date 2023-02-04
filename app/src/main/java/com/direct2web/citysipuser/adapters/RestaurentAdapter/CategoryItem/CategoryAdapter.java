package com.direct2web.citysipuser.adapters.RestaurentAdapter.CategoryItem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.activities.CommonActivies.IntroLoginActivity;
import com.direct2web.citysipuser.activities.Doctor.DoctorDashboardActivity;
import com.direct2web.citysipuser.activities.Insurance.InsurenceDashboardActivity;
import com.direct2web.citysipuser.activities.Lawyer.LawyerDashboardActivity;
import com.direct2web.citysipuser.activities.Restaurent.MainActivity;
import com.direct2web.citysipuser.activities.CommonActivies.SplashScreenActivity;
import com.direct2web.citysipuser.databinding.RawCategoryListBinding;
import com.direct2web.citysipuser.model.RestaurentModels.Category.Category;
import com.direct2web.citysipuser.utils.SessionManager;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawCategoryListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_category_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category cl = categoryList.get(position);

        Glide.with(context).load(cl.getImage())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.categoryImage);

        holder.binding.txtid.setText(cl.getId());
        holder.binding.txtName.setText(cl.getName());

        holder.binding.rlCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (cl.getId()) {
                    case "4":
                        if (new SessionManager(context).checkLogin()){
                                Intent doc_intent = new Intent(context, DoctorDashboardActivity.class);
                                context.startActivity(doc_intent);
                                new SessionManager(context).setBusinesstype("4");
                        }else {
                            Intent doc_intent = new Intent(context, IntroLoginActivity.class);
                            context.startActivity(doc_intent);
                            new SessionManager(context).setBusinesstype("4");
                        }
                        break;
                    case "6":
                        if (new SessionManager(context).checkLogin()){
                                Intent rest_intent = new Intent(context, MainActivity.class);
                                context.startActivity(rest_intent);
                                new SessionManager(context).setBusinesstype("6");
                        }else {
                            Intent rest_intent = new Intent(context, IntroLoginActivity.class);
                            context.startActivity(rest_intent);
                            new SessionManager(context).setBusinesstype("6");
                        }
                        break;

                    case "1":
                        if (new SessionManager(context).checkLogin()){
                            Intent rest_intent = new Intent(context, LawyerDashboardActivity.class);
                            context.startActivity(rest_intent);
                            new SessionManager(context).setBusinesstype("1");
                        }else {
                            Intent rest_intent = new Intent(context, IntroLoginActivity.class);
                            context.startActivity(rest_intent);
                            new SessionManager(context).setBusinesstype("1");
                        }
                        break;

                    case "3":
                        if (new SessionManager(context).checkLogin()){
                            Intent rest_intent = new Intent(context, InsurenceDashboardActivity.class);
                            context.startActivity(rest_intent);
                            new SessionManager(context).setBusinesstype("3");
                        }else {
                            Intent rest_intent = new Intent(context, IntroLoginActivity.class);
                            context.startActivity(rest_intent);
                            new SessionManager(context).setBusinesstype("3");
                        }
                        break;


                    default:
                        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        RawCategoryListBinding binding;

        public CategoryViewHolder(@NonNull RawCategoryListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
