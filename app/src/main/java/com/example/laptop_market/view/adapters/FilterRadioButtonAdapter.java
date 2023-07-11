package com.example.laptop_market.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.model.filter.Filter;
import com.example.laptop_market.utils.listeners.IFilterNewPost;
import com.example.laptop_market.utils.tables.SearchFilterPost;
import com.example.laptop_market.view.activities.FilterActivity;

import java.util.ArrayList;
import java.util.List;

public class FilterRadioButtonAdapter extends RecyclerView.Adapter<FilterRadioButtonAdapter.FilterRadioButtonViewHolder> {
    public static final int BRAND_NAME = 1;
    public static final int GUARANTEE = 2;
    public static final int CPU = 3;
    public static final int RAM = 4;
    public static final int HARD_DRIVE = 5;
    public static final int HARD_DRIVE_SIZE = 6;
    public static final int GRAPHICS = 7;
    public static final int SCREEN_SIZE = 8;

    private ArrayList<String> listcheckedRadioButton;
    private List<Filter> listFilterRadioButton;
    private IFilterNewPost.FinishGetDataFromAdapterListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public FilterRadioButtonAdapter(List<Filter> listFilterRadioButton, IFilterNewPost.FinishGetDataFromAdapterListener listener){
        this.listFilterRadioButton = listFilterRadioButton;
        this.listener = listener;
    }
    @NonNull
    @Override
    public FilterRadioButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_radio_button,parent,false);
        return new FilterRadioButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterRadioButtonViewHolder holder, int position) {
        Filter currentFilter = listFilterRadioButton.get(position);
        if(currentFilter==null){
            return;
        }

        holder.txtFilterRadioButton.setText(currentFilter.getName());
        holder.rdFilter.setSelected(true);
        holder.rdFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked)
                    listener.FinishGetDataFromAdapter(currentFilter.getName());
            }
        });
        if(currentFilter.getImage()!=700052)
        {
            holder.imgFilterRadioButton.setImageResource(currentFilter.getImage());
        }
        else {
            holder.imgFilterRadioButton.setVisibility(View.GONE);
        }
        //
        holder.txtFilterRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener !=null){
                    onItemClickListener.onItemClick(position);
                    holder.rdFilter.setSelected(true);
                }
            }
        });

        // Xử lý sự kiện click trên item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                    holder.rdFilter.setSelected(true);
                }
            }
        });

        // Xử lý sự kiện click trên RadioButton
        holder.rdFilter.setOnClickListener(new View.OnClickListener() {
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
        if(listFilterRadioButton!=null){
            return listFilterRadioButton.size();
        }
        return 0;
    }

    public static class FilterRadioButtonViewHolder extends RecyclerView.ViewHolder{
        TextView txtFilterRadioButton;
        RadioButton rdFilter;
        ImageView imgFilterRadioButton;
        public FilterRadioButtonViewHolder(@NonNull View itemView){
            super(itemView);
            txtFilterRadioButton = itemView.findViewById(R.id.txtFilterRadioButton);
            rdFilter = itemView.findViewById(R.id.rdFilter);
            imgFilterRadioButton = itemView.findViewById(R.id.imgFilterRadioButton);
        }
    }
}
