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
    int remove ;

    public DoctorCartAdapter(List<Cart> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
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

        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure want to delete ?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove = position;
                        delete("user_cart",cart.getId());
                        Toast.makeText(context, "CartId : "+cart.getId(), Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
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


    private void delete(String type, String id) {

        pd = new ProgressDialog(context);
        pd.setMessage("Please Wait....");
        pd.setCancelable(false);
        pd.show();

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponseDoctorCartItemDelete> call = api.sendDeleteCartItem("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,type, id);
        call.enqueue(new Callback<ResponseDoctorCartItemDelete>() {
            @Override
            public void onResponse(Call<ResponseDoctorCartItemDelete> call, Response<ResponseDoctorCartItemDelete> response) {

                Log.e("responseDeleteCart", new Gson().toJson(response.body()));

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body() != null && response.isSuccessful()) {

                    if (response.body().getError()) {

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        cartItems.remove(remove);
                        updateList(cartItems);
                    }

                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorCartItemDelete> call, Throwable t) {

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                t.printStackTrace();
                Log.e("errorDelete", t.getMessage());
            }
        });

    }

    private void updateList(List<Cart> cartItems) {
        this.cartItems=cartItems;
        notifyDataSetChanged();
    }

}
