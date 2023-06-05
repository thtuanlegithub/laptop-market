package com.example.laptop_market.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.laptop_market.R;
import com.example.laptop_market.activity.LoginActivity;

public class ForgotPasswordFragment extends Fragment {
    private LoginActivity loginActivity;
    private Button btnForgotPasswordBack;
    private Button btnConfirmEmail;
    public ForgotPasswordFragment(LoginActivity loginActivity) {
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
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        btnForgotPasswordBack = view.findViewById(R.id.btnForgotPasswordBack);
        btnForgotPasswordBack.setOnClickListener(v -> {
             loginActivity.replaceFragment(loginActivity.loginFragment);
        });

        btnConfirmEmail = view.findViewById(R.id.btnConfirmEmail);
        btnConfirmEmail.setOnClickListener(v -> {
            if(loginActivity.forgotPasswordOtpFragment == null){
                loginActivity.forgotPasswordOtpFragment = new ForgotPasswordOtpFragment(loginActivity);
            }
            loginActivity.replaceFragment(loginActivity.forgotPasswordOtpFragment);
        });

        return view;
    }
}