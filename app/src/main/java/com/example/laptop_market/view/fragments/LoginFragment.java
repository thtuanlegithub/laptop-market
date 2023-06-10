package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.laptop_market.R;
import com.example.laptop_market.view.activities.LoginActivity;


public class LoginFragment extends Fragment {
    public LoginActivity loginActivity;
    private Button btnLoginBack;
    private TextView txtSignUp;
    private TextView txtForgotPassword;
    public LoginFragment(LoginActivity loginActivity) {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btnLoginBack = view.findViewById(R.id.btnLoginBack);
        btnLoginBack.setOnClickListener(v -> {
            loginActivity.finish();
        });

        txtSignUp = view.findViewById(R.id.txtSignUp);
        txtSignUp.setOnClickListener(v -> {
            if(loginActivity.signUpFragment==null)
            {
                loginActivity.signUpFragment = new SignUpFragment(loginActivity);
            }
            loginActivity.replaceFragment(loginActivity.signUpFragment);
        });

        txtForgotPassword = view.findViewById(R.id.txtForgotPassword);
        txtForgotPassword.setOnClickListener(v -> {
            if (loginActivity.forgotPasswordFragment == null){
                loginActivity.forgotPasswordFragment = new ForgotPasswordFragment(loginActivity);
            }
            loginActivity.replaceFragment(loginActivity.forgotPasswordFragment);
        });
        return view;
    }
}