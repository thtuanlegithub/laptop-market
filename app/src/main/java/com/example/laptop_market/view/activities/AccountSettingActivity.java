package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.laptop_market.R;
import com.google.android.material.textfield.TextInputEditText;

public class AccountSettingActivity extends AppCompatActivity {
    private TextInputEditText edtAccountSettingPassword;
    private Button btnAccountSettingBack;
    private boolean backFromPassword = false;
    public RelativeLayout rltLayoutAccountSettingPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        btnAccountSettingBack = findViewById(R.id.btnAccountSettingBack);
        btnAccountSettingBack.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("applySlideTransition", true);
            setResult(Activity.RESULT_OK, intent);
            finish();
            //Ẩn bàn phím:
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = getCurrentFocus();
            if (inputMethodManager != null && currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        });
        rltLayoutAccountSettingPassword = findViewById(R.id.rltLayoutAccountSettingPassword);

        edtAccountSettingPassword = findViewById(R.id.edtAccountSettingPassword);
        edtAccountSettingPassword.setOnClickListener(v -> {
            Intent intent = new Intent(this,AccountPasswordActivity.class);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
            startActivity(intent,bundle);
            backFromPassword = true;
        });
    }
    public void onResume() {
        super.onResume();
        if(backFromPassword)
        {
            this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            backFromPassword=false;
        }
    }
}