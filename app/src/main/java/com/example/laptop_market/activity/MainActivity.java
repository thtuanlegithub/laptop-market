package com.example.laptop_market.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.example.laptop_market.fragment.HomeBaseFragment;
import com.example.laptop_market.R;
import com.example.laptop_market.databinding.ActivityMainBinding;
import com.example.laptop_market.fragment.AccountFragment;
import com.example.laptop_market.fragment.BuyFragment;
import com.example.laptop_market.fragment.PostFragment;
import com.example.laptop_market.fragment.SearchFragment;
import com.example.laptop_market.fragment.SearchResultFragment;
import com.example.laptop_market.fragment.SellFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    ActivityMainBinding binding;
    private SellFragment sellFragment = null;
    private HomeBaseFragment homeBaseFragment = null;
    private PostFragment postFragment = null;
    private BuyFragment buyFragment = null;
    private AccountFragment accountFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo các fragment
        homeBaseFragment = new HomeBaseFragment();
        buyFragment = new BuyFragment();
        postFragment = new PostFragment();
        accountFragment = new AccountFragment();
        sellFragment = new SellFragment();
        // Thêm fragment vào FragmentManager
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_layout, homeBaseFragment, "home")
                .add(R.id.frame_layout, buyFragment, "buy")
                .add(R.id.frame_layout, postFragment, "post")
                .add(R.id.frame_layout,sellFragment,"sell")
                .add(R.id.frame_layout, accountFragment, "account")
                .commit();

        // Ẩn tất cả các fragment, chỉ hiển thị fragment home ban đầu
        fragmentManager.beginTransaction()
                .hide(buyFragment)
                .hide(postFragment)
                .hide(accountFragment)
                .hide(sellFragment)
                .show(homeBaseFragment)
                .commit();
        // Thêm HomeFragment vào FragmentContainerView
        // getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new HomeFragment()).commit();
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    showFragment(homeBaseFragment);
                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameHomeBase);
                    if(currentFragment instanceof SearchFragment){
                        homeBaseFragment.replaceFragment(homeBaseFragment.homeFragment);
                    }
                    else if(currentFragment instanceof SearchResultFragment){
                        homeBaseFragment.replaceFragment(homeBaseFragment.homeFragment);
                    }
                    break;
                case R.id.sell:
                    showFragment(sellFragment);
                    break;
                case R.id.post:
                    showFragment(postFragment);
                    break;
                case R.id.buy:
                    showFragment(buyFragment);
                    break;
                case R.id.account:
                    showFragment(accountFragment);
                    break;
            }
            return true;
        });
    }
    private void showFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .hide(homeBaseFragment)
                .hide(buyFragment)
                .hide(postFragment)
                .hide(accountFragment)
                .hide(sellFragment)
                .show(fragment)
                .commit();
        // Ẩn bàn phím
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}