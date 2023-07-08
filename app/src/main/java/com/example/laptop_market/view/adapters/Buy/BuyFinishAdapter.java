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

public class BuyFinishAdapter extends RecyclerView.Adapter<BuyFinishAdapter.BuyFinishViewHolder> {
    private List<BuyOrder> BuyFinishList;
    public BuyFinishAdapter(List<BuyOrder> BuyFinishList){
        this.BuyFinishList = BuyFinishList;
    }

    @NonNull
    @Override
    public BuyFinishAdapter.BuyFinishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buy_order,parent,false);
        return new BuyFinishAdapter.BuyFinishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyFinishAdapter.BuyFinishViewHolder holder, int position) {
        BuyOrder BuyFinish = BuyFinishList.get(position);
        if(BuyFinish==null){
            return;
        }
        holder.imgBuyOrder.setImageBitmap(BuyFinish.getImage());
        holder.titleBuyOrder.setText(BuyFinish.getTitle());

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true); // Bật chế độ hiển thị hàng nghìn
        numberFormat.setMaximumFractionDigits(0); // Số lượng chữ số phần thập phân
        String formattedPrice = numberFormat.format(BuyFinish.getPrice());
        holder.priceBuyOrder.setText(formattedPrice);
        holder.addressBuyOrder.setText(BuyFinish.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),BuyOrderDetailActivity.class);
                intent.putExtra("BuyOrderStatus",2);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(holder.itemView.getContext(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
                holder.itemView.getContext().startActivity(intent,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(BuyFinishList!=null){
            return BuyFinishList.size();
        }
        return 0;
    }

    public class BuyFinishViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgBuyOrder;
        private TextView titleBuyOrder;
        private TextView priceBuyOrder;
        private TextView addressBuyOrder;
        public BuyFinishViewHolder(@NonNull View itemView){
            super(itemView);
            imgBuyOrder = itemView.findViewById(R.id.imgBuyOrder);
            titleBuyOrder = itemView.findViewById(R.id.titleBuyOrder);
            priceBuyOrder = itemView.findViewById(R.id.priceBuyOrder);
            addressBuyOrder = itemView.findViewById(R.id.addressBuyOrder);
        }
    }
}