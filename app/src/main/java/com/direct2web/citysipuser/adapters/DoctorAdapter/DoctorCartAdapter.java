package com.direct2web.citysipuser.adapters.DoctorAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawDoctorCartItemBinding;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.Cart;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseDoctorCartItemDelete;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorCartAdapter extends RecyclerView.Adapter<DoctorCartAdapter.ViewHolder> {

    List<Cart> cartItems;
    Context context;
    ProgressDialog pd;
    OnDeleteItemClickListner itemDeleteClicked;

    public DoctorCartAdapter(List<Cart> cartItems, Context context,OnDeleteItemClickListner itemDeleteClicked) {
        this.cartItems = cartItems;
        this.context = context;
        this.itemDeleteClicked = itemDeleteClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawDoctorCartItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_doctor_cart_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Cart cart = cartItems.get(position);
        holder.binding.txtPrice.setText(""+cart.getPrice());
        holder.binding.txtServiceName.setText(cart.getServiceName());
        holder.binding.txtAppointmentDate.setText(cart.getAppointmentDate());
        holder.binding.txtAppointmentTime.setText(cart.getAppointmentTime());
        holder.binding.txtDrName.setText(cart.getDoctorName());
        holder.binding.txtDishDescription.setText(cart.getHospitalName());

        holder.binding.btnDelete.setOnClickListener(v -> {
            itemDeleteClicked.onDeleteClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return (cartItems != null) ? cartItems.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RawDoctorCartItemBinding binding;

        public ViewHolder(@NonNull RawDoctorCartItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }




    private void updateList(List<Cart> cartItems) {
        this.cartItems=cartItems;
        notifyDataSetChanged();
    }

    public interface OnDeleteItemClickListner{
        public void onDeleteClicked(int postion);
    }

}
