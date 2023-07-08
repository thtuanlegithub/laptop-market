package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.laptop_market.R;
import com.example.laptop_market.view.adapters.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private AppCompatButton btnNotificationBack;
    private RecyclerView rcvNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        rcvNotification = findViewById(R.id.rcvNotification);
        GridLayoutManager gridLayoutManagerNotification = new GridLayoutManager(this, 1);
        rcvNotification.setLayoutManager(gridLayoutManagerNotification);
        NotificationAdapter NotificationAdapter = new NotificationAdapter(getListNotification());
        rcvNotification.setAdapter(NotificationAdapter);
        
        btnNotificationBack = findViewById(R.id.btnNotificationBack);
        btnNotificationBack.setOnClickListener(v -> {
            finish();
        });
    }
    private List<String> getListNotification(){
        List<String> listNotification = new ArrayList<>();
        listNotification.add("Laptop ASUS TUF Gaming A15 - RAM 8GB của bạn đã được khách hàng Thanh Tuấn đặt");
        listNotification.add("Laptop ASUS TUF Gaming A15 - RAM 8GB của bạn đã được khách hàng Thanh Tuấn đặt");
        listNotification.add("Laptop ASUS TUF Gaming A15 - RAM 8GB của bạn đã được khách hàng Thanh Tuấn đặt");
        listNotification.add("Laptop ASUS TUF Gaming A15 - RAM 8GB của bạn đã được khách hàng Thanh Tuấn đặt");
        listNotification.add("Laptop ASUS TUF Gaming A15 - RAM 8GB của bạn đã được khách hàng Thanh Tuấn đặt");
        return listNotification;
    }
}