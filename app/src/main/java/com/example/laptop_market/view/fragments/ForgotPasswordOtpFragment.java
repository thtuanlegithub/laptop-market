package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.laptop_market.R;
import com.example.laptop_market.view.activities.LoginActivity;

public class ForgotPasswordOtpFragment extends Fragment {
    private LoginActivity loginActivity;
    private Button btnForgotPasswordOTPBack;
    private Button btnConfirmOTP;
    public ForgotPasswordOtpFragment(LoginActivity loginActivity) {
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
        View view = inflater.inflate(R.layout.fragment_forgot_password_otp, container, false);
        btnForgotPasswordOTPBack = view.findViewById(R.id.btnForgotPasswordOTPBack);
        btnForgotPasswordOTPBack.setOnClickListener(v -> {
            loginActivity.replaceFragment(loginActivity.forgotPasswordFragment);
        });

        btnConfirmOTP = view.findViewById(R.id.btnConfirmOTP);
        btnConfirmOTP.setOnClickListener(v -> {
            if(loginActivity.forgotPasswordNewPasswordFragment==null){
                loginActivity.forgotPasswordNewPasswordFragment = new ForgotPasswordNewPasswordFragment(loginActivity);
            }
            loginActivity.replaceFragment(loginActivity.forgotPasswordNewPasswordFragment);
        });
        return view;
    }
}