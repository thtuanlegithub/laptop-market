package com.example.laptop_market.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.laptop_market.R;

public class NewPostActivity extends AppCompatActivity {
    private Button btnNewPostClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        btnNewPostClose = findViewById(R.id.btnNewPostClose);
        btnNewPostClose.setOnClickListener(view -> {
            finish();
        });
    }
}