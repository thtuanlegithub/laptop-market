package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.presenter.activities.StatisticActivityPresenter;
import com.example.laptop_market.view.fragments.BuyFinishFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.protobuf.StringValue;

import java.text.NumberFormat;

public class BuyStatisticActivity extends AppCompatActivity implements IAccountContract.View.StatisticActivityView {
    private Button btnBuyStatisticBack;
    private AppCompatButton btnViewYourFinishOrder;
    private TextView tvNoOrdersBuyStatistic, tvRevenueBuyStatistic;
    private IAccountContract.Presenter.StatisticActivityPresenter statisticActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_statistic);
        statisticActivityPresenter = new StatisticActivityPresenter(this, this);

        // Find views
        tvNoOrdersBuyStatistic = findViewById(R.id.tvNoOrdersBuyStatistic);
        tvRevenueBuyStatistic = findViewById(R.id.tvRevenueBuyStatistic);

        btnViewYourFinishOrder = findViewById(R.id.btnViewYourFinishOrder);
        btnViewYourFinishOrder.setOnClickListener(view -> {
            /*Intent intent = new Intent(this, BuyFinishFragment.class);
            startActivity(intent);*/
        });

        btnBuyStatisticBack = findViewById(R.id.btnBuyStatisticBack);
        btnBuyStatisticBack.setOnClickListener(v -> {
            finish();
        });

        InitData();
    }

    private void InitData(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String userID = firebaseUser.getUid();
            statisticActivityPresenter.LoadStatisticInformation(userID, true);
        }
    }
    @Override
    public void DisplayStatistic(int NoOrders, double revenue) {
        tvNoOrdersBuyStatistic.setText(String.valueOf(NoOrders));
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true);
        numberFormat.setMaximumFractionDigits(0);
        String formattedPrice = numberFormat.format(revenue);
        tvRevenueBuyStatistic.setText(formattedPrice + " VNĐ");
    }
}