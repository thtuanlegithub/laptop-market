package com.example.laptop_market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

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