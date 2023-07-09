package com.example.laptop_market.view.adapters;

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
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.utils.tables.PostTable;
import com.example.laptop_market.view.activities.PostDetailActivity;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;
import com.example.laptop_market.view.fragments.PostActiveFragment;

import java.text.NumberFormat;
import java.util.List;

public class PostActiveAdapter extends RecyclerView.Adapter<PostActiveAdapter.PostActiveViewHolder> {
    private List<PostSearchResult> listPostActive;
    private PostActiveFragment postActiveFragment;
    public PostActiveAdapter(List<PostSearchResult> listPostActive, PostActiveFragment postActiveFragment){
        this.listPostActive = listPostActive;
        this.postActiveFragment = postActiveFragment;
    }

    @NonNull
    @Override
    public PostActiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        return new PostActiveAdapter.PostActiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostActiveViewHolder holder, int position) {
        PostSearchResult postActive = listPostActive.get(position);
        if(postActive==null){
            return;
        }
//        holder.imgPostActive.setImageBitmap(postActive.getImage());
        holder.titlePost.setText(postActive.getTitle());
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true); // Bật chế độ hiển thị hàng nghìn
        numberFormat.setMaximumFractionDigits(0); // Số lượng chữ số phần thập phân
        String formattedPrice = numberFormat.format(postActive.getPrice());
        holder.pricePost.setText(formattedPrice + " VNĐ");
        holder.addressPost.setText(postActive.getAddress());
        holder.imgPost.setImageBitmap(postActive.getImage());
        // item select
        holder.itemView.setOnClickListener(v -> {
            // Mở Activity mới
            Intent intent = new Intent(postActiveFragment.getActivity(), PostDetailActivity.class);
            // Truyền dữ liệu cần thiết qua intent (nếu cần)
            intent.putExtra(PostTable.TABLE_NAME, postActive);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(holder.itemView.getContext(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
            postActiveFragment.startActivity(intent,bundle);
        });
    }

    @Override
    public int getItemCount() {
        if(listPostActive!=null){
            return listPostActive.size();
        }
        return 0;
    }

    public class PostActiveViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPost;
        private TextView titlePost;
        private TextView pricePost;
        private TextView addressPost;
        public PostActiveViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPost = itemView.findViewById(R.id.imgPost);
            titlePost = itemView.findViewById(R.id.titlePost);
            pricePost = itemView.findViewById(R.id.pricePost);
            addressPost = itemView.findViewById(R.id.addressPost);
        }
    }
}
