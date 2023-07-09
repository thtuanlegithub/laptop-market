package com.example.laptop_market.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_market.R;
import com.example.laptop_market.model.brand.Brand;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.utils.tables.BrandTable;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.view.fragments.HomeBaseFragment;
import com.example.laptop_market.view.fragments.SearchResultFragment;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    private final int BRAND_TYPE_HOME = 0;
    private final int BRAND_TYPE_SEARCH = 1;
    private List<Brand> listBrand;
    private HomeBaseFragment homeBaseFragment;
    private SearchResultFragment searchResultFragment;
    private PreferenceManager preferenceManager;
    public BrandAdapter(List<Brand> listBrand, SearchResultFragment searchResultFragment, Context context){
        this.listBrand = listBrand;
        this.searchResultFragment = searchResultFragment;
        preferenceManager = new PreferenceManager(context);
    }

    public BrandAdapter(List<Brand> listBrand, HomeBaseFragment homeBaseFragment, Context context) {
        this.listBrand = listBrand;
        this.homeBaseFragment = homeBaseFragment;
        this.searchResultFragment = homeBaseFragment.searchResultFragment;
        preferenceManager = new PreferenceManager(context);
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
            holder.txtBrand.setText(brand.getBrandName());
            holder.imgBrand.setImageResource(brand.getImg());
            holder.itemView.setOnClickListener(view -> {
                preferenceManager.putString(BrandTable.BRAND_NAME, brand.getBrandName());
                preferenceManager.putInt(Constants.KEY_POST_SEARCH_RESULT_TYPE,SearchResultFragment.ADAPTER_TYPE0_CLICK);
                if(homeBaseFragment.searchResultFragment == null)
                    homeBaseFragment.searchResultFragment = new SearchResultFragment(homeBaseFragment);
                homeBaseFragment.replaceFragment(homeBaseFragment.searchResultFragment);
            });
        }
        else{
            holder.txtBrand.setText(brand.getBrandName());
            holder.imgBrand.setImageResource(brand.getImg());
            holder.layoutBrand.setBackgroundResource(R.drawable.white_square);
            holder.itemView.setOnClickListener(view -> {
                preferenceManager.putString(BrandTable.BRAND_NAME, brand.getBrandName());
                preferenceManager.putInt(Constants.KEY_POST_SEARCH_RESULT_TYPE,SearchResultFragment.ADAPTER_TYPE0_CLICK);
                searchResultFragment.clickOnBrandButton();
            });
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
