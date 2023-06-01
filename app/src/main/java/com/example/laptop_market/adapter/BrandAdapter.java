package com.example.laptop_market.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout.LayoutParams;

import com.example.laptop_market.R;
import com.example.laptop_market.model.Brand;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    private int BRAND_TYPE_HOME = 0;
    private int BRAND_TYPE_SEARCH = 1;
    private List<Brand> listBrand;
    public BrandAdapter(List<Brand> listBrand){
        this.listBrand = listBrand;
    }
    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand,parent,false);
        return new BrandViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = listBrand.get(position);
        if(brand==null) {
            return;
        }
        if(brand.getType()==BRAND_TYPE_HOME) {
            holder.txtBrand.setText(brand.getName());
            holder.imgBrand.setImageResource(brand.getImage());
        }
        else{
            holder.txtBrand.setText(brand.getName());
            holder.imgBrand.setImageResource(brand.getImage());
            holder.layoutBrand.setBackgroundResource(R.drawable.white_square);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    300,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 0, 0, 0);
            holder.layoutBrand.setLayoutParams(layoutParams);
        }

    }

    @Override
    public int getItemCount() {
        if(listBrand!=null){
            return listBrand.size();
        }
        return 0;
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout layoutBrand;
        private ImageView imgBrand;
        private TextView txtBrand;
        public BrandViewHolder(@NonNull View itemView){
            super(itemView);
            imgBrand = itemView.findViewById(R.id.imgBrand);
            txtBrand = itemView.findViewById(R.id.txtBrand);
            layoutBrand = itemView.findViewById(R.id.layoutBrand);
        }
    }
}
