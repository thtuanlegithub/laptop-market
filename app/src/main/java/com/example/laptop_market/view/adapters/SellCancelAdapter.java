package com.example.laptop_market.view.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.view.activities.SellOrderDetailActivity;

import java.text.NumberFormat;
import java.util.List;

public class SellCancelAdapter extends RecyclerView.Adapter<SellCancelAdapter.SellCancelViewHolder> {
    private List<SellOrder> SellCancelList;
    public SellCancelAdapter(List<SellOrder> SellCancelList){
        this.SellCancelList = SellCancelList;
    }

    @NonNull
    @Override
    public SellCancelAdapter.SellCancelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sell_order,parent,false);
        return new SellCancelAdapter.SellCancelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellCancelAdapter.SellCancelViewHolder holder, int position) {
        SellOrder SellCancel = SellCancelList.get(position);
        if(SellCancel==null){
            return;
        }
        holder.imgSellOrder.setImageBitmap(SellCancel.getImage());
        holder.titleSellOrder.setText(SellCancel.getTitle());

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true); // Bật chế độ hiển thị hàng nghìn
        numberFormat.setMaximumFractionDigits(0); // Số lượng chữ số phần thập phân
        String formattedPrice = numberFormat.format(SellCancel.getPrice());
        holder.priceSellOrder.setText(formattedPrice);
        holder.addressSellOrder.setText(SellCancel.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),SellOrderDetailActivity.class);
                intent.putExtra("SellOrderStatus",3);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(SellCancelList!=null){
            return SellCancelList.size();
        }
        return 0;
    }

    public class SellCancelViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSellOrder;
        private TextView titleSellOrder;
        private TextView priceSellOrder;
        private TextView addressSellOrder;
        public SellCancelViewHolder(@NonNull View itemView){
            super(itemView);
            imgSellOrder = itemView.findViewById(R.id.imgSellOrder);
            titleSellOrder = itemView.findViewById(R.id.titleSellOrder);
            priceSellOrder = itemView.findViewById(R.id.priceSellOrder);
            addressSellOrder = itemView.findViewById(R.id.addressSellOrder);
        }
    }
}