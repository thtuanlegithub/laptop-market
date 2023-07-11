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

public class BuyCancelAdapter extends RecyclerView.Adapter<BuyCancelAdapter.BuyCancelViewHolder> {
    private List<BuyOrder> BuyCancelList;
    public BuyCancelAdapter(List<BuyOrder> BuyCancelList){
        this.BuyCancelList = BuyCancelList;
    }

    @NonNull
    @Override
    public BuyCancelAdapter.BuyCancelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buy_order,parent,false);
        return new BuyCancelAdapter.BuyCancelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyCancelAdapter.BuyCancelViewHolder holder, int position) {
        BuyOrder BuyCancel = BuyCancelList.get(position);
        if(BuyCancel==null){
            return;
        }
        holder.imgBuyOrder.setImageBitmap(BuyCancel.getImage());
        holder.titleBuyOrder.setText(BuyCancel.getLaptopName());

       /* NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true); // Bật chế độ hiển thị hàng nghìn
        numberFormat.setMaximumFractionDigits(0); // Số lượng chữ số phần thập phân
        String formattedPrice = numberFormat.format(BuyCancel.getPrice());*/
        holder.priceBuyOrder.setText(BuyCancel.getPrice());
        holder.addressBuyOrder.setText(BuyCancel.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),BuyOrderDetailActivity.class);
                intent.putExtra("BuyOrderStatus",3);
                intent.putExtra("ClickedOrder", BuyCancel);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(holder.itemView.getContext(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
                holder.itemView.getContext().startActivity(intent,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(BuyCancelList!=null){
            return BuyCancelList.size();
        }
        return 0;
    }

    public class BuyCancelViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgBuyOrder;
        private TextView titleBuyOrder;
        private TextView priceBuyOrder;
        private TextView addressBuyOrder;
        public BuyCancelViewHolder(@NonNull View itemView){
            super(itemView);
            imgBuyOrder = itemView.findViewById(R.id.imgBuyOrder);
            titleBuyOrder = itemView.findViewById(R.id.titleBuyOrder);
            priceBuyOrder = itemView.findViewById(R.id.priceBuyOrder);
            addressBuyOrder = itemView.findViewById(R.id.addressBuyOrder);
        }
    }
}