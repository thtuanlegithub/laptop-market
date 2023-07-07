package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.laptop_market.R;

public class SellStatisticActivity extends AppCompatActivity {
    private Button btnSellStatisticBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_statistic);

        btnSellStatisticBack = findViewById(R.id.btnSellStatisticBack);
        btnSellStatisticBack.setOnClickListener(v -> {
            finish();
        });
    }
}