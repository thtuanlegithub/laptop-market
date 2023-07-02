package com.example.laptop_market.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.model.post.Post;

import java.util.List;

public class PostActiveAdapter extends RecyclerView.Adapter<PostActiveAdapter.PostActiveViewHolder> {
    private List<Post> listPostActive;
    public PostActiveAdapter(List<Post> listPostActive){
        this.listPostActive = listPostActive;
    }

    @NonNull
    @Override
    public PostActiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_active,parent,false);
        return new PostActiveAdapter.PostActiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostActiveViewHolder holder, int position) {
        Post postActive = listPostActive.get(position);
        if(postActive==null){
            return;
        }
//        holder.imgPostActive.setImageResource(postActive.getPostMainImage());
//        holder.titlePostActive.setText(postActive.getSellerName());
//        holder.pricePostActive.setText(postActive.geSe);
//        holder.addressPostActive.setText(postActive.getAddress());
    }

    @Override
    public int getItemCount() {
        if(listPostActive!=null){
            return listPostActive.size();
        }
        return 0;
    }

    public class PostActiveViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPostActive;
        private TextView titlePostActive;
        private TextView pricePostActive;
        private TextView addressPostActive;
        public PostActiveViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPostActive = itemView.findViewById(R.id.imgPostActive);
            titlePostActive = itemView.findViewById(R.id.titlePostActive);
            pricePostActive = itemView.findViewById(R.id.pricePostActive);
            addressPostActive = itemView.findViewById(R.id.addressPostActive);
        }
    }
}
