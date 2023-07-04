package com.example.laptop_market.view.adapters.PostSearchResult;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.utils.tables.PostTable;
import com.example.laptop_market.view.activities.PostDetailActivity;
import com.example.laptop_market.view.fragments.HomeBaseFragment;

import java.text.NumberFormat;
import java.util.List;

public class PostSearchResultAdapter extends RecyclerView.Adapter<PostSearchResultAdapter.PostSearchResultViewHolder> {
    public HomeBaseFragment homeBaseFragment;
    private List<PostSearchResult> postSearchResultList;
    public PostSearchResultAdapter(List<PostSearchResult> postSearchResultList, HomeBaseFragment homeBaseFragment){
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
        PostSearchResult postSearchResult = postSearchResultList.get(position);
        if(postSearchResult==null){
            return;
        }
        holder.imgPostSearchResult.setImageBitmap(postSearchResult.getImage());
        holder.titlePostSearchResult.setText(postSearchResult.getTitle());

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true); // Bật chế độ hiển thị hàng nghìn
        numberFormat.setMaximumFractionDigits(0); // Số lượng chữ số phần thập phân
        String formattedPrice = numberFormat.format(postSearchResult.getPrice());
        holder.pricePostSearchResult.setText(formattedPrice);
        holder.addressPostSearchResult.setText(postSearchResult.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Activity mới
                Intent intent = new Intent(homeBaseFragment.getActivity(), PostDetailActivity.class);
                // Truyền dữ liệu cần thiết qua intent (nếu cần)
                intent.putExtra(PostTable.TABLE_NAME, postSearchResult);
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
