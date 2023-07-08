package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.ImageView;

import com.example.laptop_market.R;
import com.google.android.material.textfield.TextInputEditText;

public class AccountPasswordActivity extends AppCompatActivity {
    private Button btnAccountPasswordBack;
    private ImageView eyeOldPassword;
    private ImageView eyeNewPassword;
    private ImageView eyeNewPasswordConfirm;
    private TextInputEditText edtAccountSettingOldPassword;
    private TextInputEditText edtAccountSettingNewPassword;
    private TextInputEditText edtAccountSettingNewPasswordConfirm;
    private boolean eyeOldPasswordVisible = false;
    private boolean eyeNewPasswordVisible = false;
    private boolean eyeNewPasswordConfirmVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_password);

        btnAccountPasswordBack = findViewById(R.id.btnAccountPasswordBack);
        btnAccountPasswordBack.setOnClickListener(v -> {
            finish();
        });

        eyeOldPassword = findViewById(R.id.eyeOldPassword);
        eyeNewPassword = findViewById(R.id.eyeNewPassword);
        eyeNewPasswordConfirm = findViewById(R.id.eyeNewPasswordConfirm);

        edtAccountSettingOldPassword = findViewById(R.id.edtAccountSettingOldPassword);
        edtAccountSettingNewPassword = findViewById(R.id.edtAccountSettingNewPassword);
        edtAccountSettingNewPasswordConfirm = findViewById(R.id.edtAccountSettingNewPasswordConfirm);

        eyeOldPassword.setOnClickListener(v -> {
            if(!eyeOldPasswordVisible){
                eyeOldPasswordVisible = true;
                eyeOldPassword.setImageResource(R.drawable.ic_password_eye_orange); // Đặt hình ảnh mắt mở
                edtAccountSettingOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            else{
                eyeOldPasswordVisible = false;
                eyeOldPassword.setImageResource(R.drawable.ic_password_eye); // Đặt hình ảnh mắt mở
                edtAccountSettingOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            edtAccountSettingOldPassword.setSelection(edtAccountSettingOldPassword.getText().length());
        });

        eyeNewPassword.setOnClickListener(v -> {
            if(!eyeNewPasswordVisible){
                eyeNewPasswordVisible = true;
                eyeNewPassword.setImageResource(R.drawable.ic_password_eye_orange); // Đặt hình ảnh mắt mở
                edtAccountSettingNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            else{
                eyeNewPasswordVisible = false;
                eyeNewPassword.setImageResource(R.drawable.ic_password_eye); // Đặt hình ảnh mắt mở
                edtAccountSettingNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            edtAccountSettingNewPassword.setSelection(edtAccountSettingNewPassword.getText().length());
        });

        eyeNewPasswordConfirm.setOnClickListener(v -> {
            if(!eyeNewPasswordConfirmVisible){
                eyeNewPasswordConfirmVisible = true;
                eyeNewPasswordConfirm.setImageResource(R.drawable.ic_password_eye_orange); // Đặt hình ảnh mắt mở
                edtAccountSettingNewPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            else{
                eyeNewPasswordConfirmVisible = false;
                eyeNewPasswordConfirm.setImageResource(R.drawable.ic_password_eye); // Đặt hình ảnh mắt mở
                edtAccountSettingNewPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            edtAccountSettingNewPasswordConfirm.setSelection(edtAccountSettingNewPasswordConfirm.getText().length());
        });
    }
}