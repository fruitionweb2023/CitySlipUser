package com.direct2web.citysipuser.adapters.LawyerAdapter;

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
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.Slot;

import java.util.List;

public class LawyerDetailsTimeAdapter extends RecyclerView.Adapter<LawyerDetailsTimeAdapter.ViewHolder> {

    List<Slot> offerList;
    Context context;
    OnItemClickListner itemClicked;
    String  monday = "0";
    Boolean bMonday = true;

    public LawyerDetailsTimeAdapter(List<Slot> offerList, Context context, OnItemClickListner itemClicked) {
        this.offerList = offerList;
        this.context = context;
        this.itemClicked = itemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawDoctorTimingListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_doctor_timing_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Slot offers = offerList.get(position);
        holder.binding.txtTime.setText(offers.getSlot());

        holder.binding.txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.onClicked(position);
               /* holder.binding.txtTime.setBackground(context.getResources().getDrawable(R.drawable.doctor_button));
                holder.binding.txtTime.setTextColor(context.getResources().getColor(R.color.white));*/
                /*selectedItemPos = position;
                if(lastItemSelectedPos == -1)
                    lastItemSelectedPos = selectedItemPos;
                else {
                    notifyItemChanged(lastItemSelectedPos);
                    lastItemSelectedPos = selectedItemPos;
                }
                notifyItemChanged(selectedItemPos);

                if (selectedItemPos == position) {
                    holder.binding.txtTime.setBackground(context.getResources().getDrawable(R.drawable.doctor_button));
                    holder.binding.txtTime.setTextColor(context.getResources().getColor(R.color.white));
                } else {
                    holder.binding.txtTime.setBackground(context.getResources().getDrawable(R.drawable.button_2));
                    holder.binding.txtTime.setTextColor(context.getResources().getColor(R.color.clr_0059C8));
                }
                notifyItemChanged(selectedItemPos);*/
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

        RawDoctorTimingListBinding binding;

        public ViewHolder(@NonNull RawDoctorTimingListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnItemClickListner{
        public void onClicked(int postion);
    }
}
