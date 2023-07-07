package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.laptop_market.R;

public class FeedbackActivity extends AppCompatActivity {
    private Button btnFeedbackBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        btnFeedbackBack = findViewById(R.id.btnFeedbackBack);
        btnFeedbackBack.setOnClickListener(v -> {
            finish();
        });
    }
}