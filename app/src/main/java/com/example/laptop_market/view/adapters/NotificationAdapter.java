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
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.utils.tables.PostTable;
import com.example.laptop_market.view.activities.PostDetailActivity;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;

import java.text.NumberFormat;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<String> listNotification;
    public NotificationAdapter(List<String> listNotification){
        this.listNotification = listNotification;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        return new NotificationAdapter.NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        String notification = listNotification.get(position);
        if(notification==null){
            return;
        }
        holder.txtNotification.setText(notification);


        // item select
        holder.itemView.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        if(listNotification!=null){
            return listNotification.size();
        }
        return 0;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNotification;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNotification = itemView.findViewById(R.id.txtNotification);
        }
    }
}
