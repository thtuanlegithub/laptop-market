package com.example.laptop_market.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.laptop_market.view.fragments.AuthenticationFragment;
import com.example.laptop_market.view.fragments.ForgotPasswordFragment;
import com.example.laptop_market.view.fragments.ForgotPasswordNewPasswordFragment;
import com.example.laptop_market.view.fragments.ForgotPasswordOtpFragment;
import com.example.laptop_market.R;
import com.example.laptop_market.view.fragments.LoginFragment;
import com.example.laptop_market.view.fragments.SignUpFragment;


public class LoginActivity extends AppCompatActivity {
    public LoginFragment loginFragment = null;
    public SignUpFragment signUpFragment;
    public AuthenticationFragment authenticationFragment;
    public ForgotPasswordFragment forgotPasswordFragment;
    public ForgotPasswordOtpFragment forgotPasswordOtpFragment;
    public ForgotPasswordNewPasswordFragment forgotPasswordNewPasswordFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        fragmentManager = getSupportFragmentManager();

        if(loginFragment==null){
            loginFragment = new LoginFragment(this);
        }
        replaceFragment(loginFragment);

//        fragmentManager.beginTransaction()
//                .add(R.id.frame_login,loginFragment)
//                .add(R.id.frame_login,signUpFragment)
//                .add(R.id.frame_login,authenticationFragment)
//                .add(R.id.frame_login,forgotPasswordFragment)
//                .add(R.id.frame_login,forgotPasswordOtpFragment)
//                .add(R.id.frame_login,forgotPasswordNewPasswordFragment)
//                .commit();
//
//        fragmentManager.beginTransaction()
//                .hide(signUpFragment)
//                .hide(authenticationFragment)
//                .hide(forgotPasswordFragment)
//                .hide(forgotPasswordOtpFragment)
//                .hide(forgotPasswordNewPasswordFragment)
//                .show(loginFragment)
//                .commit();
    }

    public void showFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .hide(loginFragment)
                .hide(signUpFragment)
                .hide(authenticationFragment)
                .hide(forgotPasswordFragment)
                .hide(forgotPasswordOtpFragment)
                .hide(forgotPasswordNewPasswordFragment)
                .show(fragment)
                .commit();
    }

    public void replaceFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_login, fragment);
        fragmentTransaction.commit();
    }
}
