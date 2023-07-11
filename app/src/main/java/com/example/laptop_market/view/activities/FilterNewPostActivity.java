package com.example.laptop_market.view.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FilterNewPostActivity extends AppCompatActivity {
    private AppCompatButton btnFilterDetailNewPostClose;
    private TextView txtFilterNewPostClose;
    private RecyclerView rcvFilterNewPostRadioButton;
    private AppCompatButton btnFilterDetailNewPostApply;
    private PreferenceManager preferenceManager;

    private String filterName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_new_post);

        // find view
        btnFilterDetailNewPostClose = findViewById(R.id.btnFilterDetailNewPostClose);
        txtFilterNewPostClose = findViewById(R.id.txtFilterNewPostClose);
        rcvFilterNewPostRadioButton = findViewById(R.id.rcvFilterNewPostRadioButton);
        btnFilterDetailNewPostApply = findViewById(R.id.btnFilterDetailNewPostApply);
        preferenceManager = new PreferenceManager(getApplicationContext());

        //
        btnFilterDetailNewPostClose.setOnClickListener(v -> {
            finish();
        });
        Intent intent = getIntent();
        if(intent !=null && intent.hasExtra("filter") ){
            filterName = intent.getStringExtra("filter");
        }
        displayDetailFilterList();
    }
    private void displayDetailFilterList(){
        switch (filterName){
            case "Hãng +":
                //display RadioButton
                GridLayoutManager gridBrandLayoutManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridBrandLayoutManager);
                FilterRadioButtonAdapter filterBrandAdapter = new FilterRadioButtonAdapter(getListBrandFilterRadioButton(), item -> {
                    preferenceManager.putString(Constants.KEY_FILTER_TAG_NEW_POST_ADAPTER,filterName);
                    preferenceManager.putString(Constants.KEY_FILTER_NAME_NEW_POST_ADAPTER,item);
                    finish();
                });
                rcvFilterNewPostRadioButton.setAdapter(filterBrandAdapter);
                btnFilterDetailNewPostApply.setVisibility(View.GONE);
                break;
            case "Tình trạng +":
                //display RadioButton
                GridLayoutManager gridStatusLayoutManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridStatusLayoutManager);
                FilterRadioButtonAdapter filterGuaranteeAdapter = new FilterRadioButtonAdapter(getListGuaranteeFilterRadioButton(), item -> {
                    preferenceManager.putString(Constants.KEY_FILTER_TAG_NEW_POST_ADAPTER,filterName);
                    preferenceManager.putString(Constants.KEY_FILTER_NAME_NEW_POST_ADAPTER,item);
                    finish();
                });
                rcvFilterNewPostRadioButton.setAdapter(filterGuaranteeAdapter);
                btnFilterDetailNewPostApply.setVisibility(View.GONE);
                break;
            case "Bộ xử lý +":
                //display RadioButton
                GridLayoutManager gridCPULayoutManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridCPULayoutManager);
                FilterRadioButtonAdapter filterCPUAdapter = new FilterRadioButtonAdapter(getListCPUFilterRadioButton(), item -> {
                    preferenceManager.putString(Constants.KEY_FILTER_TAG_NEW_POST_ADAPTER,filterName);
                    preferenceManager.putString(Constants.KEY_FILTER_NAME_NEW_POST_ADAPTER,item);
                    finish();
                });
                rcvFilterNewPostRadioButton.setAdapter(filterCPUAdapter);
                btnFilterDetailNewPostApply.setVisibility(View.GONE);
                break;
            case "RAM +":
                //display RadioButton
                GridLayoutManager gridRAMLayoutManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridRAMLayoutManager);
                FilterRadioButtonAdapter filterRAMAdapter = new FilterRadioButtonAdapter(getListRAMFilterRadioButton(), item -> {
                    preferenceManager.putString(Constants.KEY_FILTER_TAG_NEW_POST_ADAPTER,filterName);
                    preferenceManager.putString(Constants.KEY_FILTER_NAME_NEW_POST_ADAPTER,item);
                    finish();
                });
                rcvFilterNewPostRadioButton.setAdapter(filterRAMAdapter);
                btnFilterDetailNewPostApply.setVisibility(View.GONE);
                break;
            case "Loại ổ cứng +":
                //display RadioButton
                GridLayoutManager gridHardDiskTypeLayoutManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridHardDiskTypeLayoutManager);
                FilterRadioButtonAdapter filterHardDiskTypeAdapter = new FilterRadioButtonAdapter(getListHardDiskTypeFilterRadioButton(), item -> {
                    preferenceManager.putString(Constants.KEY_FILTER_TAG_NEW_POST_ADAPTER,filterName);
                    preferenceManager.putString(Constants.KEY_FILTER_NAME_NEW_POST_ADAPTER,item);
                    finish();
                });
                rcvFilterNewPostRadioButton.setAdapter(filterHardDiskTypeAdapter);
                btnFilterDetailNewPostApply.setVisibility(View.GONE);
                break;
            case "Kích thước ổ cứng +":
                //display RadioButton
                GridLayoutManager gridHardDiskSizeLayoutManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridHardDiskSizeLayoutManager);
                FilterRadioButtonAdapter filterHardDiskSizeAdapter = new FilterRadioButtonAdapter(getListHardDiskSizeFilterRadioButton(), item -> {
                    preferenceManager.putString(Constants.KEY_FILTER_TAG_NEW_POST_ADAPTER,filterName);
                    preferenceManager.putString(Constants.KEY_FILTER_NAME_NEW_POST_ADAPTER,item);
                    finish();
                });
                rcvFilterNewPostRadioButton.setAdapter(filterHardDiskSizeAdapter);
                btnFilterDetailNewPostApply.setVisibility(View.GONE);
                break;
            case "Card màn hình +":
                //display RadioButton
                GridLayoutManager gridGraphicTypeManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridGraphicTypeManager);
                FilterRadioButtonAdapter filterGraphicTypeAdapter = new FilterRadioButtonAdapter(getListGraphicTypeFilterRadioButton(), item -> {
                    preferenceManager.putString(Constants.KEY_FILTER_TAG_NEW_POST_ADAPTER,filterName);
                    preferenceManager.putString(Constants.KEY_FILTER_NAME_NEW_POST_ADAPTER,item);
                    finish();
                });
                rcvFilterNewPostRadioButton.setAdapter(filterGraphicTypeAdapter);
                btnFilterDetailNewPostApply.setVisibility(View.GONE);
                break;
            case "Kích cỡ màn hình +":
                //display RadioButton
                GridLayoutManager gridGraphicSizeManager = new GridLayoutManager(this,1);
                rcvFilterNewPostRadioButton.setLayoutManager(gridGraphicSizeManager);
                FilterRadioButtonAdapter filterScreenSizeAdapter = new FilterRadioButtonAdapter(getListScreenSizeFilterRadioButton(), item -> {
                    preferenceManager.putString(Constants.KEY_FILTER_TAG_NEW_POST_ADAPTER,filterName);
                    preferenceManager.putString(Constants.KEY_FILTER_NAME_NEW_POST_ADAPTER,item);
                    finish();
                });
                rcvFilterNewPostRadioButton.setAdapter(filterScreenSizeAdapter);
                btnFilterDetailNewPostApply.setVisibility(View.GONE);
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
        for(int i = 0 ;i < listFilter.size(); i++)
        {
            listFilter.get(i).setTag("Hãng +");
        }
        return listFilter;
    }
    private List<Filter> getListGuaranteeFilterRadioButton(){
        List<Filter> listFilter = new ArrayList<>();
        listFilter.add(new Filter("Còn bảo hành"));
        listFilter.add(new Filter("Mới"));
        listFilter.add(new Filter("Đã sử dụng - chưa qua sửa chữa"));
        listFilter.add(new Filter("Đã qua sửa chữa"));
        for(int i = 0 ;i < listFilter.size(); i++)
        {
            listFilter.get(i).setTag("Tình trạng +");
        }
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
        for(int i = 0 ;i < filterList.size(); i++)
        {
            filterList.get(i).setTag("Kích cỡ màn hình +");
        }
        return filterList;
    }

    private List<Filter> getListGraphicTypeFilterRadioButton() {
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("Onboard"));
        filterList.add(new Filter("AMD"));
        filterList.add(new Filter("NVIDIA"));
        filterList.add(new Filter("Khác"));
        for(int i = 0 ;i < filterList.size(); i++)
        {
            filterList.get(i).setTag("Card màn hình +");
        }
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
        for(int i = 0 ;i < filterList.size(); i++)
        {
            filterList.get(i).setTag("Kích thước ổ cứng +");
        }
        return filterList;
    }

    private List<Filter> getListHardDiskTypeFilterRadioButton() {
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("SSD"));
        filterList.add(new Filter("HDD"));
        for(int i = 0 ;i < filterList.size(); i++)
        {
            filterList.get(i).setTag("Loại ổ cứng +");
        }
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
        for(int i = 0 ;i < filterList.size(); i++)
        {
            filterList.get(i).setTag("Bộ xử lý +");
        }
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
        for(int i = 0 ;i < filterList.size(); i++)
        {
            filterList.get(i).setTag("RAM +");
        }
        return filterList;
    }

}
