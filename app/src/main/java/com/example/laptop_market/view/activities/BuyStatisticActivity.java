package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.laptop_market.R;

public class BuyStatisticActivity extends AppCompatActivity {
    private Button btnBuyStatisticBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_statistic);

        btnBuyStatisticBack = findViewById(R.id.btnBuyStatisticBack);
        btnBuyStatisticBack.setOnClickListener(v -> {
            finish();
        });
    }
}