package com.direct2web.citysipuser.adapters.RestaurentAdapter.RestaurentDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawMenuListTitleBinding;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails.MenuItem;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails.MenuList;

import java.util.ArrayList;
import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {

    List<MenuList> menuList;
    Context context;
    List<MenuItem> menuItemList = new ArrayList<>();

    public MenuListAdapter(List<MenuList> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawMenuListTitleBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_menu_list_title, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MenuList menu = menuList.get(position);
        holder.binding.txtMenuName.setText(menu.getDishTypeTitle());

        menuItemList = menu.getMenuItem();

        MenuItemListAdapter orderItemAdapter = new MenuItemListAdapter(menuItemList,context, (MenuItemListAdapter.OnItemClickListner) context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        holder.binding.rclrRestaurentMenu.setLayoutManager(manager);
        holder.binding.rclrRestaurentMenu.setAdapter(orderItemAdapter);


    }

    @Override
    public int getItemCount() {
        return (menuList != null) ? menuList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RawMenuListTitleBinding binding;

        public ViewHolder(@NonNull RawMenuListTitleBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
