package com.example.laptop_market.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.example.laptop_market.R;
import com.example.laptop_market.databinding.ActivityMainBinding;
import com.example.laptop_market.fragment.AccountFragment;
import com.example.laptop_market.fragment.BuyFragment;
import com.example.laptop_market.fragment.HomeFragment;
import com.example.laptop_market.fragment.PostFragment;
import com.example.laptop_market.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private HomeFragment homeFragment = null;
    private PostFragment postFragment = null;
    private BuyFragment buyFragment = null;
    private AccountFragment accountFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        homeFragment = new HomeFragment();
        replaceFragment(homeFragment);
        // Thêm HomeFragment vào FragmentContainerView
        // getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new HomeFragment()).commit();
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    if(homeFragment==null){
                        homeFragment=new HomeFragment();
                    }
                    replaceFragment(homeFragment);
                    break;
                case R.id.post:
                    if(postFragment==null){
                        postFragment=new PostFragment();
                    }
                    replaceFragment(postFragment);
                    break;
                case R.id.buy:
                    if(buyFragment==null){
                        buyFragment=new BuyFragment();
                    }
                    replaceFragment(buyFragment);
                    break;
                case R.id.account:
                    if(accountFragment==null){
                        accountFragment=new AccountFragment();
                    }
                    replaceFragment(accountFragment);
                    break;
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        // Thêm dòng trên để ẩn bàn phím khi chuyển sang fragment mới
    }





}