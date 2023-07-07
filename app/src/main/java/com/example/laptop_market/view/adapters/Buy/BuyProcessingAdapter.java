package com.example.laptop_market.view.adapters.Buy;

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
import com.example.laptop_market.view.activities.BuyOrderDetailActivity;

import java.text.NumberFormat;
import java.util.List;

public class BuyProcessingAdapter extends RecyclerView.Adapter<BuyProcessingAdapter.BuyProcessingViewHolder> {
    private List<BuyOrder> BuyProcessingList;
    public BuyProcessingAdapter(List<BuyOrder> BuyProcessingList){
        this.BuyProcessingList = BuyProcessingList;
    }

    @NonNull
    @Override
    public BuyProcessingAdapter.BuyProcessingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buy_order,parent,false);
        return new BuyProcessingAdapter.BuyProcessingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyProcessingAdapter.BuyProcessingViewHolder holder, int position) {
        BuyOrder BuyProcessing = BuyProcessingList.get(position);
        if(BuyProcessing==null){
            return;
        }
        holder.imgBuyOrder.setImageBitmap(BuyProcessing.getImage());
        holder.titleBuyOrder.setText(BuyProcessing.getTitle());

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true); // Bật chế độ hiển thị hàng nghìn
        numberFormat.setMaximumFractionDigits(0); // Số lượng chữ số phần thập phân
        String formattedPrice = numberFormat.format(BuyProcessing.getPrice());
        holder.priceBuyOrder.setText(formattedPrice);
        holder.addressBuyOrder.setText(BuyProcessing.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),BuyOrderDetailActivity.class);
                intent.putExtra("BuyOrderStatus",0);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(holder.itemView.getContext(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
                holder.itemView.getContext().startActivity(intent,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(BuyProcessingList!=null){
            return BuyProcessingList.size();
        }
        return 0;
    }

    public class BuyProcessingViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgBuyOrder;
        private TextView titleBuyOrder;
        private TextView priceBuyOrder;
        private TextView addressBuyOrder;
        public BuyProcessingViewHolder(@NonNull View itemView){
            super(itemView);
            imgBuyOrder = itemView.findViewById(R.id.imgBuyOrder);
            titleBuyOrder = itemView.findViewById(R.id.titleBuyOrder);
            priceBuyOrder = itemView.findViewById(R.id.priceBuyOrder);
            addressBuyOrder = itemView.findViewById(R.id.addressBuyOrder);
        }
    }
}