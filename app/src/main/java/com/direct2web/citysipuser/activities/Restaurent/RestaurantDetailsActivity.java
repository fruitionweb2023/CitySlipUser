package com.direct2web.citysipuser.activities.Restaurent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.RestaurentDetails.MenuItemListAdapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.RestaurentDetails.MenuListAdapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.RestaurentDetails.RestaurentAddItemAdapter;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.RestaurentDetails.RestaurentDetailsOfferAdapter;
import com.direct2web.citysipuser.databinding.ActivityRestaurantDetailsBinding;
import com.direct2web.citysipuser.databinding.ButtomsheeetBinding;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails.MenuItem;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseAddToCart;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails.MenuList;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails.Offer;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails.RecommendedItem;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails.ResponseBusinessDetails;
import com.direct2web.citysipuser.model.RestaurentModels.WishList.WishList;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.BottomButtonClickListner;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailsActivity extends AppCompatActivity implements RestaurentAddItemAdapter.OnAddButtonClicked , MenuItemListAdapter.OnItemClickListner{

    ActivityRestaurantDetailsBinding binding;
    SessionManager sessionManager;
    ProgressDialog pd;
    List<Offer> offerList = new ArrayList<>();
    List<RecommendedItem> itemList = new ArrayList<>();
    List<MenuList> menuList = new ArrayList<>();
    RestaurentDetailsOfferAdapter adapter;
    RestaurentAddItemAdapter restaurentAddItemAdapter;
    String businessId="";
    MenuListAdapter menuListAdapter;
    BottomButtonClickListner bottomButtonClickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_details);
        sessionManager = new SessionManager(this);

        bottomButtonClickListner = new BottomButtonClickListner(this, sessionManager);

        binding.bottomnavigation.bbImgHome.setColorFilter(getResources().getColor(R.color.clr_f54748));
        binding.bottomnavigation.bbTxtHome.setTextColor(getResources().getColor(R.color.clay));

        binding.bottomnavigation.bbHome.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMyBusiness.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbOrder.setOnClickListener(new BottomButtonClickListner(this, sessionManager));
        binding.bottomnavigation.bbMenu.setOnClickListener(new BottomButtonClickListner(this, sessionManager));

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();

        businessId = getIntent().getStringExtra("business_id");

        if (businessId.equals("")){
            Toast.makeText(RestaurantDetailsActivity.this, "Please Contact Administrator", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }else {
            getRestaurentDetails(sessionManager.getUserId(),businessId);
        }

        binding.txtReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RestaurantDetailsActivity.this, AboutRestaurentActivity.class);
                i.putExtra("business_id",businessId);
                startActivity(i);
            }
        });

       binding.btnFavOrNot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b) {
                   binding.btnFavOrNot.setChecked(true);
                   sendStatus(businessId,sessionManager.getUserId(),"1");
               } else {
                   binding.btnFavOrNot.setChecked(false);
                   sendStatus(businessId,sessionManager.getUserId(),"0");
               }
           }
       });

        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Link...");
                sendIntent.putExtra(Intent.EXTRA_TITLE,binding.txtRestaurentName.getText().toString());

                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

    }

    private void getRestaurentDetails( String userId, String businessId) {

        Api api = RetrofitClient.getClient().create(Api.class);

        Call<ResponseBusinessDetails> call = api.getBusinessDetails("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, userId,businessId,"","");

        call.enqueue(new Callback<ResponseBusinessDetails>() {
            @Override
            public void onResponse(Call<ResponseBusinessDetails> call, Response<ResponseBusinessDetails> response) {

                Log.e("responseOrder", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().isError()) {

                        Toast.makeText(RestaurantDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        binding.txtRestaurentName.setText(response.body().getBusinessName());
                        binding.txtRestaurentDescription.setText(response.body().getDescription());
                        binding.txtRating.setText(response.body().getRatingStar());
                        binding.txtDistanceTime.setText(response.body().getDistanceTime());
                        binding.txtDistance.setText(response.body().getDistance());

                        offerList = response.body().getOffer();
                        adapter = new RestaurentDetailsOfferAdapter(offerList, RestaurantDetailsActivity.this);
                        binding.rcleOffer.setAdapter(adapter);

                        itemList = response.body().getRecommendedItem();

                        if (itemList.size() == 0){
                            binding.txtTitleRecommended.setVisibility(View.GONE);
                        }

                        restaurentAddItemAdapter = new RestaurentAddItemAdapter(itemList, RestaurantDetailsActivity.this,RestaurantDetailsActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RestaurantDetailsActivity.this);
                        binding.rclrRecommanded.setLayoutManager(linearLayoutManager);
                        binding.rclrRecommanded.setAdapter(restaurentAddItemAdapter);

                        menuList = response.body().getMenuList();

                        menuListAdapter = new MenuListAdapter(menuList, RestaurantDetailsActivity.this);
                        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(RestaurantDetailsActivity.this);
                        binding.rclrMenuList.setLayoutManager(linearLayoutManager1);
                        binding.rclrMenuList.setAdapter(menuListAdapter);


                        if (response.body().getWishList().equals("1")){
                            binding.btnFavOrNot.setChecked(true);
                        }else {
                            binding.btnFavOrNot.setChecked(false);
                        }


                    }

                } else {

                    Toast.makeText(RestaurantDetailsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBusinessDetails> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Log.e("error", t.getMessage());
                t.printStackTrace();

            }
        });
    }

    public void sendStatus(String businessId,String userId,String status){

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<WishList> call = api.getStatus("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key, businessId,userId,status);
        call.enqueue(new Callback<WishList>() {
            @Override
            public void onResponse(Call<WishList> call, Response<WishList> response) {

                Log.e("responseDelete", new Gson().toJson(response.body()));

                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(RestaurantDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(RestaurantDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<WishList> call, Throwable t) {
                t.printStackTrace();
                Log.e("errorDelete", t.getMessage());
            }
        });
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        LayoutInflater layoutInflater = LayoutInflater.from(RestaurantDetailsActivity.this);
        ButtomsheeetBinding binding2 = DataBindingUtil.inflate(layoutInflater,R.layout.buttomsheeet,null,false);
        bottomSheetDialog.setContentView(binding2.getRoot());
        binding2.imgDishLogo.setImageResource(R.drawable.city_sip_logo);
        bottomSheetDialog.show();

    }

    @Override
    public void onButtonClicked(int postion) {

        RecommendedItem recommendedItem = itemList.get(postion);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        LayoutInflater layoutInflater = LayoutInflater.from(RestaurantDetailsActivity.this);
        ButtomsheeetBinding binding2 = DataBindingUtil.inflate(layoutInflater,R.layout.buttomsheeet,null,false);
        bottomSheetDialog.setContentView(binding2.getRoot());
        binding2.imgDishLogo.setImageResource(R.drawable.city_sip_logo);
        bottomSheetDialog.show();

        binding2.txtRestaurentName.setText(recommendedItem.getTitle());
        binding2.txtRestaurentDescription.setText(recommendedItem.getDescription());
        binding2.txtDiscount.setText(recommendedItem.getOfferPersantage());
        binding2.txtMaxAmount.setText(recommendedItem.getOfferMaxAmount());
        binding2.txtPrice.setText(recommendedItem.getAmount());
        String itemId = recommendedItem.getId();

        Glide.with(RestaurantDetailsActivity.this)
                .load( recommendedItem.getImage())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding2.imgDishLogo);

        if (recommendedItem.getCategory().equals("Veg")){

            Glide.with(RestaurantDetailsActivity.this)
                    .load(getResources().getDrawable(R.drawable.ic_small_veg))
                    .error(R.drawable.city_sip_logo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding2.imgVegNonveg);
        } else {
            Glide.with(RestaurantDetailsActivity.this)
                    .load(getResources().getDrawable(R.drawable.ic_non_veg))
                    .error(R.drawable.city_sip_logo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding2.imgVegNonveg);
        }

        binding2.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            sendAddToCart(sessionManager.getUserId(),recommendedItem.getId());
                bottomSheetDialog.hide();
            }
        });
    }

    private void sendAddToCart(String userId,String itemId) {

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

                        Toast.makeText(RestaurantDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(RestaurantDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(RestaurantDetailsActivity.this, getResources().getString(R.string.error_admin), Toast.LENGTH_SHORT).show();

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

    @Override
    public void onItemClicked(int postion) {
        MenuItem recommendedItem = menuList.get(postion).getMenuItem().get(postion);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        LayoutInflater layoutInflater = LayoutInflater.from(RestaurantDetailsActivity.this);
        ButtomsheeetBinding binding2 = DataBindingUtil.inflate(layoutInflater,R.layout.buttomsheeet,null,false);
        bottomSheetDialog.setContentView(binding2.getRoot());
        binding2.imgDishLogo.setImageResource(R.drawable.city_sip_logo);
        bottomSheetDialog.show();

        binding2.txtRestaurentName.setText(recommendedItem.getTitle());
        binding2.txtRestaurentDescription.setText(recommendedItem.getDescription());
        binding2.txtDiscount.setText(recommendedItem.getOfferPersantage());
        binding2.txtMaxAmount.setText(recommendedItem.getOfferMaxAmount());
        binding2.txtPrice.setText(recommendedItem.getAmount());
        String itemId = recommendedItem.getId();

        Glide.with(RestaurantDetailsActivity.this)
                .load( recommendedItem.getImage())
                .error(R.drawable.city_sip_logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding2.imgDishLogo);

        if (recommendedItem.getCategory().equals("Veg")){

            Glide.with(RestaurantDetailsActivity.this)
                    .load(getResources().getDrawable(R.drawable.ic_small_veg))
                    .error(R.drawable.city_sip_logo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding2.imgVegNonveg);
        } else {
            Glide.with(RestaurantDetailsActivity.this)
                    .load(getResources().getDrawable(R.drawable.ic_non_veg))
                    .error(R.drawable.city_sip_logo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding2.imgVegNonveg);
        }


        binding2.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAddToCart(sessionManager.getUserId(),recommendedItem.getId());
                bottomSheetDialog.hide();
            }
        });
    }
}