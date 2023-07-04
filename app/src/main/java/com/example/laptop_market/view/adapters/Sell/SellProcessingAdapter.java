package com.example.laptop_market.view.adapters.Sell;

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

public class SellProcessingAdapter extends RecyclerView.Adapter<SellProcessingAdapter.SellProcessingViewHolder> {
    private List<SellOrder> SellProcessingList;
    public SellProcessingAdapter(List<SellOrder> SellProcessingList){
        this.SellProcessingList = SellProcessingList;
    }

    @NonNull
    @Override
    public SellProcessingAdapter.SellProcessingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sell_order,parent,false);
        return new SellProcessingAdapter.SellProcessingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellProcessingAdapter.SellProcessingViewHolder holder, int position) {
        SellOrder SellProcessing = SellProcessingList.get(position);
        if(SellProcessing==null){
            return;
        }
        holder.imgSellOrder.setImageBitmap(SellProcessing.getImage());
        holder.titleSellOrder.setText(SellProcessing.getTitle());

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true); // Bật chế độ hiển thị hàng nghìn
        numberFormat.setMaximumFractionDigits(0); // Số lượng chữ số phần thập phân
        String formattedPrice = numberFormat.format(SellProcessing.getPrice());
        holder.priceSellOrder.setText(formattedPrice);
        holder.addressSellOrder.setText(SellProcessing.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),SellOrderDetailActivity.class);
                intent.putExtra("SellOrderStatus",0);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(SellProcessingList!=null){
            return SellProcessingList.size();
        }
        return 0;
    }

    public class SellProcessingViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSellOrder;
        private TextView titleSellOrder;
        private TextView priceSellOrder;
        private TextView addressSellOrder;
        public SellProcessingViewHolder(@NonNull View itemView){
            super(itemView);
            imgSellOrder = itemView.findViewById(R.id.imgSellOrder);
            titleSellOrder = itemView.findViewById(R.id.titleSellOrder);
            priceSellOrder = itemView.findViewById(R.id.priceSellOrder);
            addressSellOrder = itemView.findViewById(R.id.addressSellOrder);
        }
    }
}