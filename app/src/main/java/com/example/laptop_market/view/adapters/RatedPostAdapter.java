package com.example.laptop_market.view.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
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

public class RatedPostAdapter extends RecyclerView.Adapter<RatedPostAdapter.RatedPostViewHolder> {
    private List<PostSearchResult> listRatedPost;
    public RatedPostAdapter(List<PostSearchResult> listRatedPost){
        this.listRatedPost = listRatedPost;
    }

    @NonNull
    @Override
    public RatedPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rated_post,parent,false);
        return new RatedPostAdapter.RatedPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatedPostViewHolder holder, int position) {
        PostSearchResult RatedPost = listRatedPost.get(position);
        if(RatedPost==null){
            return;
        }
//        holder.imgRatedPost.setImageBitmap(RatedPost.getImage());
        holder.titleRatedPost.setText(RatedPost.getTitle());
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true); // Bật chế độ hiển thị hàng nghìn
        numberFormat.setMaximumFractionDigits(0); // Số lượng chữ số phần thập phân
        String formattedPrice = numberFormat.format(RatedPost.getPrice());
        holder.priceRatedPost.setText(String.valueOf(formattedPrice));
        holder.addressRatedPost.setText(RatedPost.getAddress());

        // item select
        holder.itemView.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        if(listRatedPost!=null){
            return listRatedPost.size();
        }
        return 0;
    }

    public class RatedPostViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgRatedPost;
        private TextView titleRatedPost;
        private TextView priceRatedPost;
        private TextView addressRatedPost;
        private RatingBar ratingBarRatedPost;
        public RatedPostViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRatedPost = itemView.findViewById(R.id.imgRatedPost);
            titleRatedPost = itemView.findViewById(R.id.titleRatedPost);
            priceRatedPost = itemView.findViewById(R.id.priceRatedPost);
            addressRatedPost = itemView.findViewById(R.id.addressRatedPost);
            ratingBarRatedPost = itemView.findViewById(R.id.ratingBarRatedPost);
        }
    }
}
