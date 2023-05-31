package com.example.laptop_market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Thêm HomeFragment vào FragmentContainerView
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new HomeFragment()).commit();
    }
}