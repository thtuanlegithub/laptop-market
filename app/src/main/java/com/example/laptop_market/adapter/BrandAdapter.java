package com.example.laptop_market.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.model.Brand;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
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

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = listBrand.get(position);
        if(brand==null) {
            return;
        }
        holder.txtBrand.setText(brand.getName());
        holder.imgBrand.setImageResource(brand.getImage());
    }

    @Override
    public int getItemCount() {
        if(listBrand!=null){
            return listBrand.size();
        }
        return 0;
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgBrand;
        private TextView txtBrand;
        public BrandViewHolder(@NonNull View itemView){
            super(itemView);
            imgBrand = itemView.findViewById(R.id.imgBrand);
            txtBrand = itemView.findViewById(R.id.txtBrand);
        }
    }
}
