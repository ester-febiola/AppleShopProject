package com.ester.appleshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ester.appleshop.Models.MyOrderModel;
import com.ester.appleshop.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    Context context;
    List<MyOrderModel> list;

    public MyOrderAdapter(Context context, List<MyOrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getProductPrice());
        holder.price.setText(list.get(position).getProductName());
        holder.date.setText(list.get(position).getProductDate());
        holder.time.setText(list.get(position).getProductTime());
        holder.quantity.setText(list.get(position).getTotalQuantity());
        holder.totalPrice.setText("$"+list.get(position).getTotalPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,date,time,quantity,totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name_order);
            price = itemView.findViewById(R.id.product_price_order);
            date = itemView.findViewById(R.id.current_date_order);
            time = itemView.findViewById(R.id.current_time_order);
            quantity = itemView.findViewById(R.id.total_quanity_order);
            totalPrice = itemView.findViewById(R.id.total_price_order);
        }
    }
}
