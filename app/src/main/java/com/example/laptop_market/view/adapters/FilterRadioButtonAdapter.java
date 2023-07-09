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
    private SearchFilterPost searchFilterPost;
    private List<Filter> listFilterRadioButton;
    private ArrayList<String> listcheckedRadioButton;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public FilterRadioButtonAdapter(List<Filter> listFilterRadioButton, SearchFilterPost searchFilterPost, int type){
        this.listFilterRadioButton = listFilterRadioButton;
        this.searchFilterPost = searchFilterPost;
        this.listcheckedRadioButton = loadListString(type);
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
        if(listcheckedRadioButton.contains(currentFilter.getName()))
            holder.rdFilter.setChecked(true);
        holder.txtFilterRadioButton.setText(currentFilter.getName());
        holder.rdFilter.setSelected(true);
        holder.rdFilter.setTag(currentFilter.getName());
        holder.rdFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                String tag = compoundButton.getTag().toString();
                if(checked)
                {
                    listcheckedRadioButton.add(tag);
                }
                else{
                    listcheckedRadioButton.remove(tag);
                }
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

    public class FilterRadioButtonViewHolder extends RecyclerView.ViewHolder{
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
    private ArrayList<String> loadListString( int type) {
        switch (type) {
            case BRAND_NAME:
                return searchFilterPost.getListBrandName();
            case GUARANTEE:
                return searchFilterPost.getListGuarantee();
            case CPU:
                return searchFilterPost.getListCPU();
            case RAM:
                return searchFilterPost.getListRam();
            case HARD_DRIVE:
                return searchFilterPost.getListHardDrive();
            case HARD_DRIVE_SIZE:
                return searchFilterPost.getListHardDriveSize();
            case GRAPHICS:
                return searchFilterPost.getListGraphics();
            case SCREEN_SIZE:
                return searchFilterPost.getListScreenSize();
            default:
                return new ArrayList<>();
        }
    }
}
