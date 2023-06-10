package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.laptop_market.R;
import com.example.laptop_market.view.activities.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthenticationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthenticationFragment extends Fragment {
    private LoginActivity loginActivity;
    private Button btnAuthenticationBack;
    public AuthenticationFragment(LoginActivity loginActivity) {
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
        View view = inflater.inflate(R.layout.fragment_authentication, container, false);

        btnAuthenticationBack = view.findViewById(R.id.btnAuthenticationBack);
        btnAuthenticationBack.setOnClickListener(v -> {
            loginActivity.replaceFragment(loginActivity.signUpFragment);
        });


        return view;
    }
}