package com.direct2web.citysipuser.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawNotificationMenuBinding;
import com.direct2web.citysipuser.model.NotificationSetting;
import com.direct2web.citysipuser.model.ResponManageSettings;
import com.direct2web.citysipuser.utils.Api;
import com.direct2web.citysipuser.utils.RetrofitClient;
import com.direct2web.citysipuser.utils.SessionManager;
import com.direct2web.citysipuser.utils.WS_URL_PARAMS;
import com.google.gson.Gson;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    List<NotificationSetting> offerList;
    Context context;

    public SettingsAdapter(List<NotificationSetting> offerList, Context context) {
        this.offerList = offerList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawNotificationMenuBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_notification_menu, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        NotificationSetting offer = offerList.get(position);

        holder.binding.txtNotificationTitel.setText(offer.getTitle());

        holder.binding.scNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                sendStatus(new SessionManager(context).getUserId(),offer.getId(),"0");
            } else {
                sendStatus(new SessionManager(context).getUserId(),offer.getId(),"1");
            }
        });

        if (offer.getStatus().equals("1")){
            holder.binding.scNotification.setChecked(true);
        }else {
            holder.binding.scNotification.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return (offerList != null) ? offerList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RawNotificationMenuBinding binding;

        public ViewHolder(@NonNull RawNotificationMenuBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    private void updateList(List<NotificationSetting> offerList) {
        this.offerList=offerList;
        notifyDataSetChanged();
    }

    public void sendStatus(String businessId,String settingsId,String status){

        Api api = RetrofitClient.getClient().create(Api.class);
        Call<ResponManageSettings> call = api.updateSettingsStatus("Bearer " + WS_URL_PARAMS.createJWT(WS_URL_PARAMS.issuer, WS_URL_PARAMS.subject),
                WS_URL_PARAMS.access_key,businessId,settingsId,status);
        call.enqueue(new Callback<ResponManageSettings>() {
            @Override
            public void onResponse(Call<ResponManageSettings> call, Response<ResponManageSettings> response) {

                Log.e("responseStatus", new Gson().toJson(response.body()));
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getError()) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponManageSettings> call, Throwable t) {
                t.printStackTrace();
                Log.e("errorStatus", t.getMessage());
            }
        });
    }
}
