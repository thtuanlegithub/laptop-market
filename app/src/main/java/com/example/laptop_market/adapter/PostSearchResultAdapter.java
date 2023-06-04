package com.example.laptop_market.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.activity.PostDetailActivity;
import com.example.laptop_market.fragment.HomeBaseFragment;
import com.example.laptop_market.model.Post;

import java.util.List;

public class PostSearchResultAdapter extends RecyclerView.Adapter<PostSearchResultAdapter.PostSearchResultViewHolder> {
    public HomeBaseFragment homeBaseFragment;
    private List<Post> postSearchResultList;
    public PostSearchResultAdapter(List<Post> postSearchResultList, HomeBaseFragment homeBaseFragment){
        this.postSearchResultList = postSearchResultList;
        this.homeBaseFragment = homeBaseFragment;
    }

    @NonNull
    @Override
    public PostSearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_search_result,parent,false);
        return new PostSearchResultAdapter.PostSearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostSearchResultViewHolder holder, int position) {
        Post postSearchResult = postSearchResultList.get(position);
        if(postSearchResult==null){
            return;
        }
        holder.imgPostSearchResult.setImageResource(postSearchResult.getImg());
        holder.titlePostSearchResult.setText(postSearchResult.getLaptopName());
        holder.pricePostSearchResult.setText(postSearchResult.getPrice());
        holder.addressPostSearchResult.setText(postSearchResult.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Activity mới
                Intent intent = new Intent(homeBaseFragment.getActivity(), PostDetailActivity.class);
                // Truyền dữ liệu cần thiết qua intent (nếu cần)
                homeBaseFragment.startActivity(intent);
            }
        });
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
