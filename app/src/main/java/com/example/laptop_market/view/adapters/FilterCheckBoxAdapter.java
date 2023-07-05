package com.example.laptop_market.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.model.filter.Filter;
import com.example.laptop_market.utils.tables.SearchFilterPost;
import com.example.laptop_market.view.activities.FilterActivity;

import java.util.ArrayList;
import java.util.List;

public class FilterCheckBoxAdapter extends RecyclerView.Adapter<FilterCheckBoxAdapter.FilterCheckBoxViewHolder> {
    public static final int BRAND_NAME = 1;
    public static final int GUARANTEE = 2;
    public static final int CPU = 3;
    public static final int RAM = 4;
    public static final int HARD_DRIVE = 5;
    public static final int HARD_DRIVE_SIZE = 6;
    public static final int GRAPHICS = 7;
    public static final int SCREEN_SIZE = 8;
    private SearchFilterPost searchFilterPost;
    private List<Filter> listFilterCheckBox;
    private ArrayList<String> listcheckedCheckBox;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public FilterCheckBoxAdapter(List<Filter> listFilterCheckBox, SearchFilterPost searchFilterPost, int type){
        this.listFilterCheckBox = listFilterCheckBox;
        this.searchFilterPost = searchFilterPost;
        this.listcheckedCheckBox = loadListString(type);
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
        if(listcheckedCheckBox.contains(currentFilter.getName()))
            holder.cbFilter.setChecked(true);
        holder.txtFilterCheckBox.setText(currentFilter.getName());
        holder.cbFilter.setSelected(true);
        holder.cbFilter.setTag(currentFilter.getName());
        holder.cbFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                String tag = compoundButton.getTag().toString();
                if(checked)
                {
                    listcheckedCheckBox.add(tag);
                }
                else{
                    listcheckedCheckBox.remove(tag);
                }
            }
        });
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
