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
import com.example.laptop_market.view.fragments.PostInactiveFragment;

import java.text.NumberFormat;
import java.util.List;

public class PostInactiveAdapter extends RecyclerView.Adapter<PostInactiveAdapter.PostInactiveViewHolder> {
    private List<PostSearchResult> listPostInactive;
    private PostInactiveFragment postInactiveFragment;
    public PostInactiveAdapter(List<PostSearchResult> listPostInactive, PostInactiveFragment postInactiveFragment){
        this.listPostInactive = listPostInactive;
        this.postInactiveFragment = postInactiveFragment;
    }

    @NonNull
    @Override
    public PostInactiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        return new PostInactiveAdapter.PostInactiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostInactiveViewHolder holder, int position) {
        PostSearchResult postInactive = listPostInactive.get(position);
        if(postInactive==null){
            return;
        }
//        holder.imgPostInactive.setImageBitmap(postInactive.getImage());
        holder.titlePost.setText(postInactive.getTitle());
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true); // Bật chế độ hiển thị hàng nghìn
        numberFormat.setMaximumFractionDigits(0); // Số lượng chữ số phần thập phân
        String formattedPrice = numberFormat.format(postInactive.getPrice());
        holder.pricePost.setText(formattedPrice + " VNĐ");
        holder.addressPost.setText(postInactive.getAddress());
        holder.imgPost.setImageBitmap(postInactive.getImage());
        holder.inactiveLabelPost.setVisibility(View.VISIBLE);
        // item select
        holder.itemView.setOnClickListener(v -> {
            // Mở Activity mới
            Intent intent = new Intent(postInactiveFragment.getActivity(), PostDetailActivity.class);
            // Truyền dữ liệu cần thiết qua intent (nếu cần)
            intent.putExtra(PostTable.TABLE_NAME, postInactive);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(holder.itemView.getContext(), R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
            postInactiveFragment.startActivity(intent,bundle);
        });
    }

    @Override
    public int getItemCount() {
        if(listPostInactive!=null){
            return listPostInactive.size();
        }
        return 0;
    }

    public class PostInactiveViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPost;
        private TextView titlePost;
        private TextView pricePost;
        private TextView addressPost;
        private TextView inactiveLabelPost;
        public PostInactiveViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPost = itemView.findViewById(R.id.imgPost);
            titlePost = itemView.findViewById(R.id.titlePost);
            pricePost = itemView.findViewById(R.id.pricePost);
            addressPost = itemView.findViewById(R.id.addressPost);
            inactiveLabelPost = itemView.findViewById(R.id.inactiveLabelPost);
        }
    }
}
