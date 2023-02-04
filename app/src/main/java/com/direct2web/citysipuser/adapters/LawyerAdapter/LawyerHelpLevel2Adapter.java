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
import com.direct2web.citysipuser.databinding.RawHelpAndFaqsBinding;
import com.direct2web.citysipuser.model.LawyerModels.HelpAndFaqs.Level2.Level2;

import java.util.List;

public class LawyerHelpLevel2Adapter extends RecyclerView.Adapter<LawyerHelpLevel2Adapter.ViewHolder> {

    List<Level2> addressListItem;
    Context context;
    OnItemClickListner itemClicked;

    public LawyerHelpLevel2Adapter(List<Level2> addressListItem, Context context, OnItemClickListner itemClicked) {
        this.addressListItem = addressListItem;
        this.context = context;
        this.itemClicked = itemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawHelpAndFaqsBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_help_and_faqs, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Level2 addressItem = addressListItem.get(position);
        holder.binding.txtGeneralIssues.setText(addressItem.getLevel2Title());

        holder.binding.txtGeneralIssuesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicked.onClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (addressListItem != null) ? addressListItem.size() : 0;
    }

    public class ViewHolder   extends RecyclerView.ViewHolder  {

        RawHelpAndFaqsBinding binding;

        public ViewHolder(@NonNull RawHelpAndFaqsBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface OnItemClickListner{
        public void onClicked(int postion);
    }
}
