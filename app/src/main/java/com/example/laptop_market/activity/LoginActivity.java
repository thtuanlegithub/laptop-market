package com.example.laptop_market.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.example.laptop_market.R;
import com.example.laptop_market.databinding.ActivityLoginBinding;
import com.example.laptop_market.fragment.AuthenticationFragment;
import com.example.laptop_market.fragment.ForgotPasswordFragment;
import com.example.laptop_market.fragment.ForgotPasswordNewPasswordFragment;
import com.example.laptop_market.fragment.ForgotPasswordOtpFragment;
import com.example.laptop_market.fragment.LoginFragment;
import com.example.laptop_market.fragment.SignUpFragment;

public class LoginActivity extends AppCompatActivity {
    private LoginFragment loginFragment;
    private SignUpFragment signUpFragment;
    private AuthenticationFragment authenticationFragment;
    private ForgotPasswordFragment forgotPasswordFragment;
    private ForgotPasswordOtpFragment forgotPasswordOtpFragment;
    private ForgotPasswordNewPasswordFragment forgotPasswordNewPasswordFragment;
    private FragmentManager fragmentManager;
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginFragment = new LoginFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_login,loginFragment,"login")
                .add(R.id.frame_login,signUpFragment,"signup")
                .add(R.id.frame_login,authenticationFragment,"authentication")
                .add(R.id.frame_login,forgotPasswordFragment,"forgotpassword")
                .add(R.id.frame_login,forgotPasswordOtpFragment,"forgotpasswordotp")
                .add(R.id.frame_login,forgotPasswordNewPasswordFragment,"forgotpasswordnewpassword")
                .commit();

        fragmentManager.beginTransaction()
                .hide(signUpFragment)
                .hide(authenticationFragment)
                .hide(forgotPasswordFragment)
                .hide(forgotPasswordOtpFragment)
                .hide(forgotPasswordNewPasswordFragment)
                .commit();

    }

    private void showFragment(Fragment fragment){
        fragmentManager.beginTransaction()
                .hide(loginFragment)
                .hide(signUpFragment)
                .hide(authenticationFragment)
                .hide(forgotPasswordFragment)
                .hide(forgotPasswordOtpFragment)
                .hide(forgotPasswordNewPasswordFragment)
                .show(fragment)
                .commit();
        // Ẩn bàn phím
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}