package com.direct2web.citysipuser.adapters.RestaurentAdapter.Cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawCartItemsBinding;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<CartItem> cartItems;
    Context context;
    int number = 0;
    int price ;
    String totel ;
    String totelItemsAmount = "";
    onPlusButtonClicked onPlusButtonClicked;
    onMinusButtonClicked onMinusButtonClicked;

    public CartAdapter(List<CartItem> cartItems, Context context, onPlusButtonClicked onPlusButtonClicked,onMinusButtonClicked onMinusButtonClicked) {
        this.cartItems = cartItems;
        this.context = context;
        this.onPlusButtonClicked = onPlusButtonClicked;
        this.onMinusButtonClicked = onMinusButtonClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawCartItemsBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_cart_items, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CartItem cart = cartItems.get(position);
        holder.binding.txtPrice.setText(cart.getPrice());
        holder.binding.txtDishName.setText(cart.getTitle());
        holder.binding.txtQty.setText(cart.getQty());

        if (cart.getCategory().equals("Veg")){

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



        price = position;
        totel =  position  + cartItems.get(position).getPrice();

        holder.binding.btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   inc(false,holder.binding.txtQty,holder.binding.txtPrice,price);*/
                onMinusButtonClicked.onMinus(position);

            }
        });
        holder.binding.btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    inc(true,holder.binding.txtQty,holder.binding.txtPrice,price);*/
                onPlusButtonClicked.onPlus(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (cartItems != null) ? cartItems.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RawCartItemsBinding binding;

        public ViewHolder(@NonNull RawCartItemsBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    private void inc(Boolean x, TextView t2, TextView t1,int price1) {
        int number = Integer.parseInt(t2.getText().toString());
        int price = Integer.parseInt(cartItems.get(price1).getPrice());
        if (x) {
            number++;

            t2.setText(String.valueOf(number));

            t1.setText(String.valueOf(price * number));

        } else {
            number--;
            if (number == 0) {
            } else {
                t2.setText(String.valueOf(number));
                t1.setText(String.valueOf(price * number));
            }
        }
    }
    public interface onPlusButtonClicked{
        public void onPlus(int postion);
    }
    public interface onMinusButtonClicked{
        public void onMinus(int postion);
    }

}
