package com.example.laptop_market.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.model.Brand;
import com.example.laptop_market.model.Filter;

import java.util.List;

public class FilterCheckBoxAdapter extends RecyclerView.Adapter<FilterCheckBoxAdapter.FilterCheckBoxViewHolder> {
    private List<Filter> listFilterCheckBox;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public FilterCheckBoxAdapter(List<Filter> listFilterCheckBox){
        this.listFilterCheckBox = listFilterCheckBox;
    }
    @NonNull
    @Override
    public FilterCheckBoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_check_box,parent,false);
        return new FilterCheckBoxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterCheckBoxViewHolder holder, int position) {
        Filter currentFilter = listFilterCheckBox.get(position);
        if(currentFilter==null){
            return;
        }
        holder.txtFilterCheckBox.setText(currentFilter.getName());
        holder.cbFilter.setSelected(true);
        if(currentFilter.getImage()!=700052)
        {
            holder.imgFilterCheckBox.setImageResource(currentFilter.getImage());
        }
        else {
            holder.imgFilterCheckBox.setVisibility(View.GONE);
        }
        //
        holder.txtFilterCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener !=null){
                    onItemClickListener.onItemClick(position);
                    holder.cbFilter.setSelected(true);
                }
            }
        });

        // Xử lý sự kiện click trên item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                    holder.cbFilter.setSelected(true);
                }
            }
        });

        // Xử lý sự kiện click trên CheckBox
        holder.cbFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listFilterCheckBox!=null){
            return listFilterCheckBox.size();
        }
        return 0;
    }

    public class FilterCheckBoxViewHolder extends RecyclerView.ViewHolder{
        TextView txtFilterCheckBox;
        CheckBox cbFilter;
        ImageView imgFilterCheckBox;
        public FilterCheckBoxViewHolder(@NonNull View itemView){
            super(itemView);
            txtFilterCheckBox = itemView.findViewById(R.id.txtFilterCheckBox);
            cbFilter = itemView.findViewById(R.id.cbFilter);
            imgFilterCheckBox = itemView.findViewById(R.id.imgFilterCheckBox);
        }
    }
}
