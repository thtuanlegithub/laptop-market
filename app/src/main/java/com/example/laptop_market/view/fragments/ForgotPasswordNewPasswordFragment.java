package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.view.activities.LoginActivity;

public class ForgotPasswordNewPasswordFragment extends Fragment {
    private LoginActivity loginActivity;
    private Button btnNewPaswordBack;
    private Button btnConfirmNewPassword;
    public ForgotPasswordNewPasswordFragment(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password_new_password, container, false);
        btnNewPaswordBack = view.findViewById(R.id.btnNewPasswordBack);
        btnNewPaswordBack.setOnClickListener(v -> {
            loginActivity.replaceFragment(loginActivity.forgotPasswordOtpFragment);
        });

        btnConfirmNewPassword = view.findViewById(R.id.btnConfirmNewPassword);
        btnConfirmNewPassword.setOnClickListener(v -> {
            Toast.makeText(loginActivity, "Đặt lại mật khẩu thành công, bạn có thể đăng nhập bằng mật khẩu mới!", Toast.LENGTH_SHORT).show();
            loginActivity.replaceFragment(loginActivity.loginFragment);
        });

        return view;
    }
}