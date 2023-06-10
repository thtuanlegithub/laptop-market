package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.laptop_market.R;

public class NewPostActivity extends AppCompatActivity {
    private Button btnNewPostClose;
    private RelativeLayout newPostStatus;
    private RelativeLayout newPostBrand;
    private RelativeLayout newPostCPU;
    private RelativeLayout newPostRAM;
    private RelativeLayout newPostHardDiskType;
    private RelativeLayout newPostHardDiskSize;
    private RelativeLayout newPostGraphicType;
    private RelativeLayout newPostGraphicSize;
    private RelativeLayout newPostWarranty;
    private RelativeLayout newPostOriginCountry;
    private EditText edtNewPostPrice;
    private EditText edtTitle;
    private EditText edtPhoneNumber;
    private EditText edtSellerName;
    private EditText edtSellerAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        btnNewPostClose = findViewById(R.id.btnNewPostClose);
        btnNewPostClose.setOnClickListener(view -> {
            finish();
        });

        findView();

    }

    private void findView(){
        newPostStatus = findViewById(R.id.newPostStatus);
        newPostBrand = findViewById(R.id.newPostBrand);
        newPostCPU = findViewById(R.id.newPostCPU);
        newPostRAM = findViewById(R.id.newPostRAM);
        newPostHardDiskType = findViewById(R.id.newPostHardDiskType);
        newPostHardDiskSize = findViewById(R.id.newPostGraphicSize);
        newPostGraphicType = findViewById(R.id.newPostGraphicType);
        newPostGraphicSize = findViewById(R.id.newPostGraphicSize);
        newPostWarranty = findViewById(R.id.newPostWarranty);
        newPostOriginCountry = findViewById(R.id.newPostOriginCountry);
        edtNewPostPrice = findViewById(R.id.edtNewPostPrice);
        edtTitle = findViewById(R.id.edtTitle);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtSellerAddress = findViewById(R.id.edtSellerAddress);
        edtSellerName = findViewById(R.id.edtSellerName);
    }
}