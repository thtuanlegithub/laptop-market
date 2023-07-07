package com.example.laptop_market.view.adapters.Sell;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.view.activities.SellOrderDetailActivity;

import java.text.NumberFormat;
import java.util.List;

public class SellDeliveringAdapter extends RecyclerView.Adapter<SellDeliveringAdapter.SellDeliveringViewHolder> {
    private List<SellOrder> SellDeliveringList;
    public SellDeliveringAdapter(List<SellOrder> SellDeliveringList){
        this.SellDeliveringList = SellDeliveringList;
    }

    @NonNull
    @Override
    public SellDeliveringAdapter.SellDeliveringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sell_order,parent,false);
        return new SellDeliveringAdapter.SellDeliveringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellDeliveringAdapter.SellDeliveringViewHolder holder, int position) {
        SellOrder SellDelivering = SellDeliveringList.get(position);
        if(SellDelivering==null){
            return;
        }
        holder.imgSellOrder.setImageBitmap(SellDelivering.getImage());
        holder.titleSellOrder.setText(SellDelivering.getTitle());

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true); // Bật chế độ hiển thị hàng nghìn
        numberFormat.setMaximumFractionDigits(0); // Số lượng chữ số phần thập phân
        String formattedPrice = numberFormat.format(SellDelivering.getPrice());
        holder.priceSellOrder.setText(formattedPrice);
        holder.addressSellOrder.setText(SellDelivering.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),SellOrderDetailActivity.class);
                intent.putExtra("SellOrderStatus",1);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(holder.itemView.getContext(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
                holder.itemView.getContext().startActivity(intent,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(SellDeliveringList!=null){
            return SellDeliveringList.size();
        }
        return 0;
    }

    public class SellDeliveringViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSellOrder;
        private TextView titleSellOrder;
        private TextView priceSellOrder;
        private TextView addressSellOrder;
        public SellDeliveringViewHolder(@NonNull View itemView){
            super(itemView);
            imgSellOrder = itemView.findViewById(R.id.imgSellOrder);
            titleSellOrder = itemView.findViewById(R.id.titleSellOrder);
            priceSellOrder = itemView.findViewById(R.id.priceSellOrder);
            addressSellOrder = itemView.findViewById(R.id.addressSellOrder);
        }
    }
}