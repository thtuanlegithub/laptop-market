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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    private Button btnSignUpBack;
    private Button btnSignUp;
    private TextView txtLogin;
    public LoginActivity loginActivity;

    public SignUpFragment(LoginActivity loginActivity) {
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        btnSignUpBack = view.findViewById(R.id.btnSignUpBack);
        btnSignUpBack.setOnClickListener(v -> {
            loginActivity.replaceFragment(loginActivity.loginFragment);
        });

        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            loginActivity.authenticationFragment = new AuthenticationFragment(loginActivity);
            loginActivity.replaceFragment(loginActivity.authenticationFragment);
        });

        txtLogin = view.findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(v -> {
            loginActivity.replaceFragment(loginActivity.loginFragment);
        });

        return view;
    }
}