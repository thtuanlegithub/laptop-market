package com.example.laptop_market.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.laptop_market.R;
import com.example.laptop_market.activity.LoginActivity;


public class LoginFragment extends Fragment {
    public LoginActivity loginActivity;
    private Button btnLoginBack;
    private TextView txtSignUp;
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
            loginActivity.signUpFragment = new SignUpFragment(loginActivity);
            loginActivity.replaceFragment(loginActivity.signUpFragment);
        });
        return view;
    }
}