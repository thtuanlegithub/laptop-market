package com.example.laptop_market.adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.model.PostSearchResult;

import java.util.List;

public class PostSearchResultAdapter extends RecyclerView.Adapter<PostSearchResultAdapter.PostSearchResultViewHolder> {
    private List<PostSearchResult> postSearchResultList;
    public PostSearchResultAdapter(List<PostSearchResult> postSearchResultList){
        this.postSearchResultList = postSearchResultList;
    }

    @NonNull
    @Override
    public PostSearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_search_result,parent,false);
        return new PostSearchResultAdapter.PostSearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostSearchResultViewHolder holder, int position) {
        PostSearchResult postSearchResult = postSearchResultList.get(position);
        if(postSearchResult==null){
            return;
        }
        holder.imgPostSearchResult.setImageResource(postSearchResult.getImgPostSearchResult());
        holder.titlePostSearchResult.setText(postSearchResult.getTitle());
        holder.pricePostSearchResult.setText(postSearchResult.getPrice());
        holder.addressPostSearchResult.setText(postSearchResult.getAddress());
    }

    @Override
    public int getItemCount() {
        if(postSearchResultList!=null){
            return postSearchResultList.size();
        }
        return 0;
    }

    public class PostSearchResultViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPostSearchResult;
        private TextView titlePostSearchResult;
        private TextView pricePostSearchResult;
        private TextView addressPostSearchResult;
        public PostSearchResultViewHolder(@NonNull View itemView){
            super(itemView);
            imgPostSearchResult = itemView.findViewById(R.id.imgPostSearchResult);
            titlePostSearchResult = itemView.findViewById(R.id.titlePostSearchResult);
            pricePostSearchResult = itemView.findViewById(R.id.pricePostSearchResult);
            addressPostSearchResult = itemView.findViewById(R.id.addressPostSearchResult);
        }
    }
}
