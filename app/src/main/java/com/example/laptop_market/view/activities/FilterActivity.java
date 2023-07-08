package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.utils.tables.SearchFilterPost;
import com.example.laptop_market.view.adapters.FilterCheckBoxAdapter;
import com.example.laptop_market.model.filter.Filter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    private String filterName;
    private LinearLayout layoutSeekBar;
    private RecyclerView rcvFilterCheckbox;
    private Button btnFilterDetailClose;
    private Button btnFilterApply;
    private SearchFilterPost searchFilterPost;
    private SeekBar seekBarMinPrice;
    private SeekBar seekBarMaxPrice;
    private TextView minimumPriceTextView;
    private TextView maximumPriceTextView;
    public FilterActivity()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        //
        btnFilterApply = findViewById(R.id.btnFilterDetailApply);
        btnFilterDetailClose = findViewById(R.id.btnFilterDetailClose);
        rcvFilterCheckbox = findViewById(R.id.rcvFilterCheckbox);
        layoutSeekBar = findViewById(R.id.layoutSeekBar);
        seekBarMinPrice = findViewById(R.id.seekBarMinPrice);
        seekBarMaxPrice = findViewById(R.id.seekBarMaxPrice);
        minimumPriceTextView = findViewById(R.id.minimumPriceTextView);
        maximumPriceTextView = findViewById(R.id.maximumPriceTextView);
        rcvFilterCheckbox.setVisibility(View.VISIBLE);
        layoutSeekBar.setVisibility(View.GONE);
        btnFilterApply.setVisibility(View.VISIBLE);
        //
        Intent intent = getIntent();
        if(intent !=null && intent.hasExtra("filter") && intent.hasExtra(SearchFilterPost.SEARCH_NAME)){
            filterName = intent.getStringExtra("filter");
            searchFilterPost = (SearchFilterPost)intent.getSerializableExtra(SearchFilterPost.SEARCH_NAME);
        }
        setListener();
        displayDetailFilterList();
        initSeekBar();

    }
    private void initSeekBar()
    {
        int min = searchFilterPost.getMinimumPrice();
        int max = searchFilterPost.getMaximumPrice();
        seekBarMaxPrice.setProgress(max);
        seekBarMinPrice.setProgress(min);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String minValueFormatted = formatter.format(min);
        String maxValueFormatted = formatter.format(max);
        minimumPriceTextView.setText(minValueFormatted);
        maximumPriceTextView.setText(maxValueFormatted);
    }
    private void setListener()
    {
        btnFilterApply.setOnClickListener(view -> {
            String minValueFormatted = minimumPriceTextView.getText().toString();
            minValueFormatted = minValueFormatted.replaceAll("\\.", "");
            minValueFormatted = minValueFormatted.replaceAll(",", "");
            String maxValueFormatted = maximumPriceTextView.getText().toString();
            maxValueFormatted = maxValueFormatted.replaceAll("\\.", "");
            maxValueFormatted = maxValueFormatted.replaceAll(",", "");
            int minValue = Integer.parseInt(minValueFormatted);
            int maxValue = Integer.parseInt(maxValueFormatted);
            if(maxValue < minValue || maxValue == 0)
            {
                Toast.makeText(getApplicationContext(), "Giá tiền không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            searchFilterPost.setMinimumPrice(minValue);
            searchFilterPost.setMaximumPrice(maxValue);
            PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
            preferenceManager.putSerializable(Constants.KEY_FILTER_SEARCH,searchFilterPost);
            finish();
        });
        btnFilterDetailClose.setOnClickListener(v -> {
            finish();
        });
        seekBarMinPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Thực hiện cập nhật giá trị tối thiểu tương ứng với giá trị của SeekBar
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String minValueFormatted = formatter.format(progress);
                minimumPriceTextView.setText(minValueFormatted);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Không cần thực hiện gì trong phương thức này
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Không cần thực hiện gì trong phương thức này
            }
        });
        seekBarMaxPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Thực hiện cập nhật giá trị tối thiểu tương ứng với giá trị của SeekBar
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String minValueFormatted = formatter.format(progress);
                maximumPriceTextView.setText(minValueFormatted);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Không cần thực hiện gì trong phương thức này
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Không cần thực hiện gì trong phương thức này
            }
        });
    }

    private void displayDetailFilterList(){
        switch (filterName){
            case "Giá +":
                layoutSeekBar.setVisibility(View.VISIBLE);
                rcvFilterCheckbox.setVisibility(View.GONE);

                break;
            case "Hãng +":
                layoutSeekBar.setVisibility(View.GONE);
                rcvFilterCheckbox.setVisibility(View.VISIBLE);
                //display CheckBox
                GridLayoutManager gridBrandLayoutManager = new GridLayoutManager(this,1);
                rcvFilterCheckbox.setLayoutManager(gridBrandLayoutManager);
                FilterCheckBoxAdapter filterBrandAdapter = new FilterCheckBoxAdapter(getListBrandFilterCheckBox(),searchFilterPost,FilterCheckBoxAdapter.BRAND_NAME);
                rcvFilterCheckbox.setAdapter(filterBrandAdapter);
                break;
            case "Tình trạng +":
                layoutSeekBar.setVisibility(View.GONE);
                rcvFilterCheckbox.setVisibility(View.VISIBLE);
                //display CheckBox
                GridLayoutManager gridStatusLayoutManager = new GridLayoutManager(this,1);
                rcvFilterCheckbox.setLayoutManager(gridStatusLayoutManager);
                FilterCheckBoxAdapter filterGuaranteeAdapter = new FilterCheckBoxAdapter(getListGuaranteeFilterCheckBox(),searchFilterPost,FilterCheckBoxAdapter.GUARANTEE);
                rcvFilterCheckbox.setAdapter(filterGuaranteeAdapter);

                break;
            case "Bộ xử lý +":
                layoutSeekBar.setVisibility(View.GONE);
                rcvFilterCheckbox.setVisibility(View.VISIBLE);
                //display CheckBox
                GridLayoutManager gridCPULayoutManager = new GridLayoutManager(this,1);
                rcvFilterCheckbox.setLayoutManager(gridCPULayoutManager);
                FilterCheckBoxAdapter filterCPUAdapter = new FilterCheckBoxAdapter(getListCPUFilterCheckBox(),searchFilterPost,FilterCheckBoxAdapter.CPU);
                rcvFilterCheckbox.setAdapter(filterCPUAdapter);

                break;
            case "RAM +":
                layoutSeekBar.setVisibility(View.GONE);
                rcvFilterCheckbox.setVisibility(View.VISIBLE);
                //display CheckBox
                GridLayoutManager gridRAMLayoutManager = new GridLayoutManager(this,1);
                rcvFilterCheckbox.setLayoutManager(gridRAMLayoutManager);
                FilterCheckBoxAdapter filterRAMAdapter = new FilterCheckBoxAdapter(getListRAMFilterCheckBox(),searchFilterPost,FilterCheckBoxAdapter.RAM);
                rcvFilterCheckbox.setAdapter(filterRAMAdapter);

                break;
            case "Loại ổ cứng +":
                layoutSeekBar.setVisibility(View.GONE);
                rcvFilterCheckbox.setVisibility(View.VISIBLE);
                //display CheckBox
                GridLayoutManager gridHardDiskTypeLayoutManager = new GridLayoutManager(this,1);
                rcvFilterCheckbox.setLayoutManager(gridHardDiskTypeLayoutManager);
                FilterCheckBoxAdapter filterHardDiskTypeAdapter = new FilterCheckBoxAdapter(getListHardDiskTypeFilterCheckBox(),searchFilterPost,FilterCheckBoxAdapter.HARD_DRIVE);
                rcvFilterCheckbox.setAdapter(filterHardDiskTypeAdapter);

                break;
            case "Kích thước ổ cứng +":
                layoutSeekBar.setVisibility(View.GONE);
                rcvFilterCheckbox.setVisibility(View.VISIBLE);
                //display CheckBox
                GridLayoutManager gridHardDiskSizeLayoutManager = new GridLayoutManager(this,1);
                rcvFilterCheckbox.setLayoutManager(gridHardDiskSizeLayoutManager);
                FilterCheckBoxAdapter filterHardDiskSizeAdapter = new FilterCheckBoxAdapter(getListHardDiskSizeFilterCheckBox(),searchFilterPost,FilterCheckBoxAdapter.HARD_DRIVE_SIZE);
                rcvFilterCheckbox.setAdapter(filterHardDiskSizeAdapter);
                break;
            case "Card màn hình +":
                layoutSeekBar.setVisibility(View.GONE);
                rcvFilterCheckbox.setVisibility(View.VISIBLE);
                //display CheckBox
                GridLayoutManager gridGraphicTypeManager = new GridLayoutManager(this,1);
                rcvFilterCheckbox.setLayoutManager(gridGraphicTypeManager);
                FilterCheckBoxAdapter filterGraphicTypeAdapter = new FilterCheckBoxAdapter(getListGraphicTypeFilterCheckBox(),searchFilterPost,FilterCheckBoxAdapter.GRAPHICS);
                rcvFilterCheckbox.setAdapter(filterGraphicTypeAdapter);
                break;
            case "Kích cỡ màn hình +":
                layoutSeekBar.setVisibility(View.GONE);
                rcvFilterCheckbox.setVisibility(View.VISIBLE);
                //display CheckBox
                GridLayoutManager gridGraphicSizeManager = new GridLayoutManager(this,1);
                rcvFilterCheckbox.setLayoutManager(gridGraphicSizeManager);
                FilterCheckBoxAdapter filterScreenSizeAdapter = new FilterCheckBoxAdapter(getListScreenSizeFilterCheckBox(),searchFilterPost,FilterCheckBoxAdapter.SCREEN_SIZE);
                rcvFilterCheckbox.setAdapter(filterScreenSizeAdapter);
                break;
        }
    }
    private List<Filter> getListBrandFilterCheckBox() {
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
    private List<Filter> getListGuaranteeFilterCheckBox(){
        List<Filter> listFilter = new ArrayList<>();
        listFilter.add(new Filter("Còn bảo hành"));
        listFilter.add(new Filter("Mới"));
        listFilter.add(new Filter("Đã sử dụng - chưa qua sửa chữa"));
        listFilter.add(new Filter("Đã qua sửa chữa"));
        return listFilter;
    }
    private List<Filter> getListScreenSizeFilterCheckBox() {
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

    private List<Filter> getListGraphicTypeFilterCheckBox() {
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("Onboard"));
        filterList.add(new Filter("AMD"));
        filterList.add(new Filter("NVIDIA"));
        filterList.add(new Filter("Khác"));
        return filterList;
    }

    private List<Filter> getListHardDiskSizeFilterCheckBox() {
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

    private List<Filter> getListHardDiskTypeFilterCheckBox() {
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("SSD"));
        filterList.add(new Filter("HDD"));
        return filterList;
    }

    private List<Filter> getListCPUFilterCheckBox() {
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
    private List<Filter> getListRAMFilterCheckBox() {
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