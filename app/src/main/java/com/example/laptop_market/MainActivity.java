package com.example.laptop_market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.laptop_market.databinding.ActivityMainBinding;

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
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}