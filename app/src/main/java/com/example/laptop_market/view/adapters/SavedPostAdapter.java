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

public class SavedPostAdapter extends RecyclerView.Adapter<SavedPostAdapter.SavedPostViewHolder> {
    private List<PostSearchResult> listSavedPost;
    public SavedPostAdapter(List<PostSearchResult> listSavedPost){
        this.listSavedPost = listSavedPost;
    }

    @NonNull
    @Override
    public SavedPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        return new SavedPostAdapter.SavedPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedPostViewHolder holder, int position) {
        PostSearchResult SavedPost = listSavedPost.get(position);
        if(SavedPost==null){
            return;
        }
//        holder.imgSavedPost.setImageBitmap(SavedPost.getImage());
        holder.titlePost.setText(SavedPost.getTitle());
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true); // Bật chế độ hiển thị hàng nghìn
        numberFormat.setMaximumFractionDigits(0); // Số lượng chữ số phần thập phân
        String formattedPrice = numberFormat.format(SavedPost.getPrice());
        holder.pricePost.setText(String.valueOf(formattedPrice));
        holder.addressPost.setText(SavedPost.getAddress());


        // item select
        holder.itemView.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        if(listSavedPost!=null){
            return listSavedPost.size();
        }
        return 0;
    }

    public class SavedPostViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPost;
        private TextView titlePost;
        private TextView pricePost;
        private TextView addressPost;
        public SavedPostViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPost = itemView.findViewById(R.id.imgPost);
            titlePost = itemView.findViewById(R.id.titlePost);
            pricePost = itemView.findViewById(R.id.pricePost);
            addressPost = itemView.findViewById(R.id.addressPost);
        }
    }
}