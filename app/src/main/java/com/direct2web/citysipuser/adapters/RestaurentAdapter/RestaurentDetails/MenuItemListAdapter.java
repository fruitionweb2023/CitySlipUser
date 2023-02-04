package com.direct2web.citysipuser.adapters.RestaurentAdapter.RestaurentDetails;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
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
import com.direct2web.citysipuser.activities.Restaurent.RestaurantDetailsActivity;
import com.direct2web.citysipuser.databinding.ButtomsheeetBinding;
import com.direct2web.citysipuser.databinding.RawOrderItemBinding;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails.MenuItem;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseAddToCart;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuItemListAdapter extends RecyclerView.Adapter<MenuItemListAdapter.ViewHolder> {

    List<MenuItem> itemList;
    Context context;
   OnItemClickListner itemClicked;
    private ProgressDialog pd;


    public MenuItemListAdapter(List<MenuItem> itemList, Context context,OnItemClickListner itemClicked) {
        this.itemList = itemList;
        this.context = context;
        this.itemClicked = itemClicked;
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
        MenuItem items = itemList.get(position);
        holder.binding.txtOffer.setText(items.getOfferPersantage() + "% off");
        holder.binding.txtDishName.setText(items.getTitle());
        holder.binding.txtDishDescription.setText(items.getDescription());
        holder.binding.txtDiscount.setText(items.getOfferPersantage());
        holder.binding.txtMaxAmount.setText(items.getOfferMaxAmount());
        holder.binding.txtPrice.setText(items.getAmount());
        holder.binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                itemClicked.onItemClicked(position);

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

                LayoutInflater layoutInflater = LayoutInflater.from(context);
                ButtomsheeetBinding binding2 = DataBindingUtil.inflate(layoutInflater,R.layout.buttomsheeet,null,false);
                bottomSheetDialog.setContentView(binding2.getRoot());
                binding2.imgDishLogo.setImageResource(R.drawable.city_sip_logo);
                bottomSheetDialog.show();

                binding2.txtRestaurentName.setText(items.getTitle());
                binding2.txtRestaurentDescription.setText(items.getDescription());
                binding2.txtDiscount.setText(items.getOfferPersantage());
                binding2.txtMaxAmount.setText(items.getOfferMaxAmount());
                binding2.txtPrice.setText(items.getAmount());
                String itemId = items.getId();

                Glide.with(context)
                        .load( items.getImage())
                        .error(R.drawable.city_sip_logo)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(binding2.imgDishLogo);

                if (items.getCategory().equals("Veg")){

                    Glide.with(context)
                            .load(context.getResources().getDrawable(R.drawable.ic_small_veg))
                            .error(R.drawable.city_sip_logo)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(binding2.imgVegNonveg);
                } else {
                    Glide.with(context)
                            .load(context.getResources().getDrawable(R.drawable.ic_non_veg))
                            .error(R.drawable.city_sip_logo)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(binding2.imgVegNonveg);
                }


                binding2.btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendAddToCart(new SessionManager(context).getUserId(),items.getId());
                        bottomSheetDialog.hide();
                    }
                });

            }
        });

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

    }

    @Override
    public int getItemCount() {
        return (itemList != null) ? itemList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RawOrderItemBinding binding;

        public ViewHolder(@NonNull RawOrderItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnItemClickListner{
        public void onItemClicked(int postion);

    }

    private void sendAddToCart(String userId,String itemId) {

        pd = new ProgressDialog(context);
        pd.setMessage("Please Wait....");
        pd.setCancelable(false);
        pd.show();

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseAddToCart> call = api.sendAddToCart("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,userId,itemId);

        call.enqueue(new Callback<ResponseAddToCart>() {
            @Override
            public void onResponse(Call<ResponseAddToCart> call, Response<ResponseAddToCart> response) {

                Log.e("responseAddtoCart", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().isError()) {

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(context, context.getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseAddToCart> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }


}
