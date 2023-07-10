package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.presenter.activities.StatisticActivityPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.NumberFormat;

public class SellStatisticActivity extends AppCompatActivity implements IAccountContract.View.StatisticActivityView {
    private Button btnSellStatisticBack;
    private AppCompatButton btnViewYourFinishOrder;
    private TextView tvNoOrdersSellStatistic, tvRevenueSellStatistic;
    private IAccountContract.Presenter.StatisticActivityPresenter statisticActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_statistic);
        statisticActivityPresenter = new StatisticActivityPresenter(this, this);

        // Find views
        tvNoOrdersSellStatistic = findViewById(R.id.tvNoOrdersSellStatistic);
        tvRevenueSellStatistic = findViewById(R.id.tvRevenueSellStatistic);

        btnViewYourFinishOrder = findViewById(R.id.btnViewYourFinishOrder);
        btnViewYourFinishOrder.setOnClickListener(view -> {

        });

        btnSellStatisticBack = findViewById(R.id.btnSellStatisticBack);
        btnSellStatisticBack.setOnClickListener(v -> {
            finish();
        });

        InitData();
    }
    private void InitData(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String userID = firebaseUser.getUid();
            statisticActivityPresenter.LoadStatisticInformation(userID, false);
        }
    }
    @Override
    public void DisplayStatistic(int NoOrders, double revenue) {
        tvNoOrdersSellStatistic.setText(String.valueOf(NoOrders));
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true);
        numberFormat.setMaximumFractionDigits(0);
        String formattedPrice = numberFormat.format(revenue);
        tvRevenueSellStatistic.setText(formattedPrice + " VNĐ");
    }
}