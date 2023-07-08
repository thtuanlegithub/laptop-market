package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.presenter.activities.AccountPasswordActivityPresenter;
import com.google.android.material.textfield.TextInputEditText;

public class AccountPasswordActivity extends AppCompatActivity implements IAccountContract.View.AccountPasswordActivityView{
    private Button btnAccountPasswordBack;
    private ImageView eyeOldPassword;
    private ImageView eyeNewPassword;
    private ImageView eyeNewPasswordConfirm;
    private TextInputEditText edtAccountSettingOldPassword;
    private TextInputEditText edtAccountSettingNewPassword;
    private TextInputEditText edtAccountSettingNewPasswordConfirm;
    private AppCompatButton btnConfirmPassword;
    private boolean eyeOldPasswordVisible = false;
    private boolean eyeNewPasswordVisible = false;
    private boolean eyeNewPasswordConfirmVisible = false;
    private boolean isEnableChangePassword;
    private IAccountContract.Presenter.AccountPasswordActivityPresenter accountPasswordActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_password);
        isEnableChangePassword = false;
        accountPasswordActivityPresenter = new AccountPasswordActivityPresenter(this);
        btnAccountPasswordBack = findViewById(R.id.btnAccountPasswordBack);

        btnConfirmPassword = findViewById(R.id.btnConfirmPassword);
        eyeOldPassword = findViewById(R.id.eyeOldPassword);
        eyeNewPassword = findViewById(R.id.eyeNewPassword);
        eyeNewPasswordConfirm = findViewById(R.id.eyeNewPasswordConfirm);
        edtAccountSettingOldPassword = findViewById(R.id.edtAccountSettingOldPassword);
        edtAccountSettingNewPassword = findViewById(R.id.edtAccountSettingNewPassword);
        edtAccountSettingNewPasswordConfirm = findViewById(R.id.edtAccountSettingNewPasswordConfirm);
        setListener();

    }
    private void setListener()
    {
        btnAccountPasswordBack.setOnClickListener(v -> {
            finish();
        });
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
        btnConfirmPassword.setOnClickListener(view -> {
            if(!edtAccountSettingNewPasswordConfirm.getText().toString().equals(edtAccountSettingNewPassword.getText().toString()))
                Toast.makeText(getApplicationContext(), "Mật khẩu mới với mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            else{
                accountPasswordActivityPresenter.UpdatePassword(edtAccountSettingOldPassword.getText().toString(),
                        edtAccountSettingNewPassword.getText().toString());
            }
        });
        edtAccountSettingOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edtAccountSettingNewPasswordConfirm.getText().toString().isEmpty() || edtAccountSettingNewPassword.getText().toString().isEmpty() ||
                        edtAccountSettingOldPassword.getText().toString().isEmpty())
                    isEnableChangePassword = false;
                else
                    isEnableChangePassword = true;
                checkEnableConfirmButton();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtAccountSettingNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edtAccountSettingNewPasswordConfirm.getText().toString().isEmpty() || edtAccountSettingNewPassword.getText().toString().isEmpty() ||
                        edtAccountSettingOldPassword.getText().toString().isEmpty())
                    isEnableChangePassword = false;
                else
                    isEnableChangePassword = true;
                checkEnableConfirmButton();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtAccountSettingNewPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edtAccountSettingNewPasswordConfirm.getText().toString().isEmpty() || edtAccountSettingNewPassword.getText().toString().isEmpty() ||
                        edtAccountSettingOldPassword.getText().toString().isEmpty())
                    isEnableChangePassword = false;
                else
                    isEnableChangePassword = true;
                checkEnableConfirmButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void checkEnableConfirmButton()
    {
        if(isEnableChangePassword) {
            btnConfirmPassword.setEnabled(true);
            btnConfirmPassword.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.yellow));
        }
        else {
            btnConfirmPassword.setEnabled(false);
            btnConfirmPassword.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
        }
    }
    @Override
    public void UpdatePasswordSuccess() {
        Toast.makeText(this, "Đổi password thành công", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void WrongOldPassword(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}