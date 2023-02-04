package com.direct2web.citysipuser.adapters.InsurenceAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawDoctorTimingListBinding;
import com.direct2web.citysipuser.databinding.RawInsurenceTimingListDetailsBinding;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.Slot;

import java.util.List;

public class InsurenceDetailsTimeListAdapter extends RecyclerView.Adapter<InsurenceDetailsTimeListAdapter.ViewHolder> {

    List<Slot> offerList;
    Context context;
    OnItemClickListner itemClicked;
    private int amountOfItemsSelected=0;
    private int row_of_index = -1;
    int index = 0;
    String  monday = "0";
    Boolean bMonday = true;

    public InsurenceDetailsTimeListAdapter(List<Slot> offerList, Context context, OnItemClickListner itemClicked) {
        this.offerList = offerList;
        this.context = context;
        this.itemClicked = itemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawInsurenceTimingListDetailsBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_insurence_timing_list_details, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Slot offers = offerList.get(position);
        holder.binding.txtTime.setText(offers.getSlot());

        holder.binding.txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {

                    case R.id.txt_time:
                        if (bMonday) {
                            bMonday = false;
                            monday = "1";

                            holder.binding.txtTime.setBackground(context.getResources().getDrawable(R.drawable.doctor_button));
                            holder.binding.txtTime.setTextColor(context.getResources().getColor(R.color.white));
                        } else {
                            bMonday = true;
                            monday = "0";

                            holder.binding.txtTime.setBackground(context.getResources().getDrawable(R.drawable.button_2));
                            holder.binding.txtTime.setTextColor(context.getResources().getColor(R.color.clr_0059C8));
                        }
                }
            }
        });
        }


    @Override
    public int getItemCount() {
        return (offerList != null) ? offerList.size() : 0;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder  {

        RawInsurenceTimingListDetailsBinding binding;

        public ViewHolder(@NonNull RawInsurenceTimingListDetailsBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnItemClickListner{
        public void onClicked(int postion);
    }
}
