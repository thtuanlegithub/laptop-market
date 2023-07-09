package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.laptop_market.R;
import com.example.laptop_market.model.filter.Filter;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.utils.tables.SearchFilterPost;
import com.example.laptop_market.view.adapters.FilterRadioButtonAdapter;

import java.util.ArrayList;
import java.util.List;

public class FilterNewPostActivity extends AppCompatActivity {
    private AppCompatButton btnFilterDetailNewPostClose;
    private TextView txtFilterNewPostClose;
    private RecyclerView rcvFilterNewPostRadioButton;
    private LinearLayout layoutSeekBarNewPost;
    private TextView minimumPriceTextViewNewPost;
    private SeekBar seekBarMinPriceNewPost;
    private TextView maximumPriceTextViewNewPost;
    private SeekBar seekBarMaxPriceNewPost;
    private AppCompatButton btnFilterDetailNewPostApply;
    private String filterName;
    private SearchFilterPost searchFilterPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_new_post);

        // find view
        btnFilterDetailNewPostClose = findViewById(R.id.btnFilterDetailNewPostClose);
        txtFilterNewPostClose = findViewById(R.id.txtFilterNewPostClose);
        rcvFilterNewPostRadioButton = findViewById(R.id.rcvFilterNewPostRadioButton);
        layoutSeekBarNewPost = findViewById(R.id.layoutSeekBarNewPost);
        minimumPriceTextViewNewPost = findViewById(R.id.minimumPriceTextViewNewPost);
        seekBarMinPriceNewPost = findViewById(R.id.seekBarMinPriceNewPost);
        maximumPriceTextViewNewPost = findViewById(R.id.maximumPriceTextViewNewPost);
        seekBarMaxPriceNewPost = findViewById(R.id.seekBarMaxPriceNewPost);
        btnFilterDetailNewPostApply = findViewById(R.id.btnFilterDetailNewPostApply);
        //
        btnFilterDetailNewPostClose.setOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        if(intent !=null && intent.hasExtra("filter") && intent.hasExtra(SearchFilterPost.SEARCH_NAME)){
            filterName = intent.getStringExtra("filter");
            searchFilterPost = (SearchFilterPost)intent.getSerializableExtra(SearchFilterPost.SEARCH_NAME);
        }
        displayDetailFilterList();
    }
    private void displayDetailFilterList(){
        switch (filterName){
            case "Giá +":
                layoutSeekBarNewPost.setVisibility(View.VISIBLE);
                rcvFilterNewPostRadioButton.setVisibility(View.GONE);

                break;
            case "Hãng +":
                layoutSeekBarNewPost.setVisibility(View.GONE);
                rcvFilterNewPostRadioButton.setVisibility(View.VISIBLE);
                //display RadioButton
                GridLayoutManager gridBrandLayoutManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridBrandLayoutManager);
                FilterRadioButtonAdapter filterBrandAdapter = new FilterRadioButtonAdapter(getListBrandFilterRadioButton(),searchFilterPost,FilterRadioButtonAdapter.BRAND_NAME);
                rcvFilterNewPostRadioButton.setAdapter(filterBrandAdapter);
                break;
            case "Tình trạng +":
                layoutSeekBarNewPost.setVisibility(View.GONE);
                rcvFilterNewPostRadioButton.setVisibility(View.VISIBLE);
                //display RadioButton
                GridLayoutManager gridStatusLayoutManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridStatusLayoutManager);
                FilterRadioButtonAdapter filterGuaranteeAdapter = new FilterRadioButtonAdapter(getListGuaranteeFilterRadioButton(),searchFilterPost,FilterRadioButtonAdapter.GUARANTEE);
                rcvFilterNewPostRadioButton.setAdapter(filterGuaranteeAdapter);

                break;
            case "Bộ xử lý +":
                layoutSeekBarNewPost.setVisibility(View.GONE);
                rcvFilterNewPostRadioButton.setVisibility(View.VISIBLE);
                //display RadioButton
                GridLayoutManager gridCPULayoutManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridCPULayoutManager);
                FilterRadioButtonAdapter filterCPUAdapter = new FilterRadioButtonAdapter(getListCPUFilterRadioButton(),searchFilterPost,FilterRadioButtonAdapter.CPU);
                rcvFilterNewPostRadioButton.setAdapter(filterCPUAdapter);

                break;
            case "RAM +":
                layoutSeekBarNewPost.setVisibility(View.GONE);
                rcvFilterNewPostRadioButton.setVisibility(View.VISIBLE);
                //display RadioButton
                GridLayoutManager gridRAMLayoutManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridRAMLayoutManager);
                FilterRadioButtonAdapter filterRAMAdapter = new FilterRadioButtonAdapter(getListRAMFilterRadioButton(),searchFilterPost,FilterRadioButtonAdapter.RAM);
                rcvFilterNewPostRadioButton.setAdapter(filterRAMAdapter);

                break;
            case "Loại ổ cứng +":
                layoutSeekBarNewPost.setVisibility(View.GONE);
                rcvFilterNewPostRadioButton.setVisibility(View.VISIBLE);
                //display RadioButton
                GridLayoutManager gridHardDiskTypeLayoutManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridHardDiskTypeLayoutManager);
                FilterRadioButtonAdapter filterHardDiskTypeAdapter = new FilterRadioButtonAdapter(getListHardDiskTypeFilterRadioButton(),searchFilterPost,FilterRadioButtonAdapter.HARD_DRIVE);
                rcvFilterNewPostRadioButton.setAdapter(filterHardDiskTypeAdapter);

                break;
            case "Kích thước ổ cứng +":
                layoutSeekBarNewPost.setVisibility(View.GONE);
                rcvFilterNewPostRadioButton.setVisibility(View.VISIBLE);
                //display RadioButton
                GridLayoutManager gridHardDiskSizeLayoutManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridHardDiskSizeLayoutManager);
                FilterRadioButtonAdapter filterHardDiskSizeAdapter = new FilterRadioButtonAdapter(getListHardDiskSizeFilterRadioButton(),searchFilterPost,FilterRadioButtonAdapter.HARD_DRIVE_SIZE);
                rcvFilterNewPostRadioButton.setAdapter(filterHardDiskSizeAdapter);
                break;
            case "Card màn hình +":
                layoutSeekBarNewPost.setVisibility(View.GONE);
                rcvFilterNewPostRadioButton.setVisibility(View.VISIBLE);
                //display RadioButton
                GridLayoutManager gridGraphicTypeManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridGraphicTypeManager);
                FilterRadioButtonAdapter filterGraphicTypeAdapter = new FilterRadioButtonAdapter(getListGraphicTypeFilterRadioButton(),searchFilterPost,FilterRadioButtonAdapter.GRAPHICS);
                rcvFilterNewPostRadioButton.setAdapter(filterGraphicTypeAdapter);
                break;
            case "Kích cỡ màn hình +":
                layoutSeekBarNewPost.setVisibility(View.GONE);
                rcvFilterNewPostRadioButton.setVisibility(View.VISIBLE);
                //display RadioButton
                GridLayoutManager gridGraphicSizeManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridGraphicSizeManager);
                FilterRadioButtonAdapter filterScreenSizeAdapter = new FilterRadioButtonAdapter(getListScreenSizeFilterRadioButton(),searchFilterPost,FilterRadioButtonAdapter.SCREEN_SIZE);
                rcvFilterNewPostRadioButton.setAdapter(filterScreenSizeAdapter);
                break;
        }
    }
    private List<Filter> getListBrandFilterRadioButton() {
        List<Filter> listFilter = new ArrayList<>();
        listFilter.add(new Filter(R.drawable.brand_logo_apple,"Apple"));
        listFilter.add(new Filter(R.drawable.brand_logo_asus,"Asus"));
        listFilter.add(new Filter(R.drawable.brand_logo_dell,"Dell"));
        listFilter.add(new Filter(R.drawable.brand_logo_hp,"HP"));
        listFilter.add(new Filter(R.drawable.brand_logo_lenovo,"Lenovo"));
        listFilter.add(new Filter(R.drawable.brand_logo_lg,"LG"));
        listFilter.add(new Filter(R.drawable.brand_logo_acer,"Acer"));
        listFilter.add(new Filter(R.drawable.brand_logo_msi,"MSI"));
        listFilter.add(new Filter(R.drawable.brand_logo_razer,"Razer"));
        listFilter.add(new Filter(R.drawable.brand_logo_samsung,"Samsung"));
        listFilter.add(new Filter(R.drawable.brand_logo_sony,"Sony"));
        listFilter.add(new Filter(R.drawable.brand_logo_toshiba,"Toshiba"));
        listFilter.add(new Filter(R.drawable.ic_baseline_more_horiz_24,"Khác"));
        return listFilter;
    }
    private List<Filter> getListGuaranteeFilterRadioButton(){
        List<Filter> listFilter = new ArrayList<>();
        listFilter.add(new Filter("Còn bảo hành"));
        listFilter.add(new Filter("Mới"));
        listFilter.add(new Filter("Đã sử dụng - chưa qua sửa chữa"));
        listFilter.add(new Filter("Đã qua sửa chữa"));
        return listFilter;
    }
    private List<Filter> getListScreenSizeFilterRadioButton() {
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("< 9 inch"));
        filterList.add(new Filter("9 - 10.9 inch"));
        filterList.add(new Filter("11 - 12.9 inch"));
        filterList.add(new Filter("13 - 14.9 inch"));
        filterList.add(new Filter("15 - 16.9 inch"));
        filterList.add(new Filter("17 - 18.9 inch"));
        filterList.add(new Filter("19 - 20.9 inch"));
        filterList.add(new Filter(">= 21 inch"));
        return filterList;
    }

    private List<Filter> getListGraphicTypeFilterRadioButton() {
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("Onboard"));
        filterList.add(new Filter("AMD"));
        filterList.add(new Filter("NVIDIA"));
        filterList.add(new Filter("Khác"));
        return filterList;
    }

    private List<Filter> getListHardDiskSizeFilterRadioButton() {
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("< 128 GB"));
        filterList.add(new Filter("250 GB"));
        filterList.add(new Filter("256 GB"));
        filterList.add(new Filter("320 GB"));
        filterList.add(new Filter("480 GB"));
        filterList.add(new Filter("500 GB"));
        filterList.add(new Filter("512 GB"));
        filterList.add(new Filter("640 GB"));
        filterList.add(new Filter("700 GB"));
        filterList.add(new Filter("750 GB"));
        filterList.add(new Filter("1 TB"));
        filterList.add(new Filter("> 1 TB"));
        return filterList;
    }

    private List<Filter> getListHardDiskTypeFilterRadioButton() {
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("SSD"));
        filterList.add(new Filter("HDD"));
        return filterList;
    }

    private List<Filter> getListCPUFilterRadioButton() {
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("AMD"));
        filterList.add(new Filter("Athlon"));
        filterList.add(new Filter("Intel Atom"));
        filterList.add(new Filter("Intel Core 2 Duo"));
        filterList.add(new Filter("Intel Core 2 Quad"));
        filterList.add(new Filter("Intel Core i3"));
        filterList.add(new Filter("Intel Core i5"));
        filterList.add(new Filter("Intel Core i7"));
        filterList.add(new Filter("Intel Core i9"));
        filterList.add(new Filter("Intel Pentium"));
        filterList.add(new Filter("Intel Quark"));
        filterList.add(new Filter("Intel Xeon"));
        filterList.add(new Filter("Ryzen 3"));
        filterList.add(new Filter("Ryzen 5"));
        filterList.add(new Filter("Ryzen 7"));
        filterList.add(new Filter("Ryzen 9"));
        filterList.add(new Filter("Khác"));
        return filterList;
    }
    private List<Filter> getListRAMFilterRadioButton() {
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("< 1GB"));
        filterList.add(new Filter("1 GB"));
        filterList.add(new Filter("2 GB"));
        filterList.add(new Filter("4 GB"));
        filterList.add(new Filter("6 GB"));
        filterList.add(new Filter("8 GB"));
        filterList.add(new Filter("16 GB"));
        filterList.add(new Filter("32 GB"));
        filterList.add(new Filter("> 32 GB"));
        return filterList;
    }

}
