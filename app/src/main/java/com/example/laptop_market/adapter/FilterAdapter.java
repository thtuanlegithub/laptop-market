package com.example.laptop_market.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.model.Filter;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {
    private List<Filter> listFilter;
    public FilterAdapter(List<Filter> listFilter){
        this.listFilter = listFilter;
    }
    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter,parent,false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        Filter filter = listFilter.get(position);
        if(filter==null) {
            return;
        }
        holder.btnFilter.setText(filter.getName());
    }

    @Override
    public int getItemCount() {
        if(listFilter!=null){
            return listFilter.size();
        }
        return 0;
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder{
        private Button btnFilter;
        public FilterViewHolder(@NonNull View itemView){
            super(itemView);
           btnFilter = itemView.findViewById(R.id.btnFilter);
        }
    }
}

