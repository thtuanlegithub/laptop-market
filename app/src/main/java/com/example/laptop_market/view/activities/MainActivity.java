package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.laptop_market.contracts.IFragmentListener;
import com.example.laptop_market.databinding.ActivityMainBinding;
import com.example.laptop_market.utils.elses.ConnectionReceiver;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.view.fragments.AccountBaseFragment;
import com.example.laptop_market.view.fragments.HomeBaseFragment;
import com.example.laptop_market.R;
import com.example.laptop_market.view.fragments.BuyFragment;
import com.example.laptop_market.view.fragments.InternetDisconnectedFragment;
import com.example.laptop_market.view.fragments.PostFragment;
import com.example.laptop_market.view.fragments.SearchFragment;
import com.example.laptop_market.view.fragments.SearchResultFragment;
import com.example.laptop_market.view.fragments.SellFragment;

public class MainActivity extends AppCompatActivity implements IFragmentListener {
    private FragmentManager fragmentManager;
    ActivityMainBinding binding;
    private ConnectionReceiver broadcastReceiver = null;
    private Fragment currentFragment = null;
    private InternetDisconnectedFragment internetDisconnectedFragment;
    private SellFragment sellFragment = null;
    private HomeBaseFragment homeBaseFragment = null;
    private PostFragment postFragment = null;
    private BuyFragment buyFragment = null;
    private AccountBaseFragment accountBaseFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo các fragment
        homeBaseFragment = new HomeBaseFragment();
        buyFragment = new BuyFragment();
        postFragment = new PostFragment();
        accountBaseFragment = new AccountBaseFragment();
        sellFragment = new SellFragment();
        internetDisconnectedFragment = new InternetDisconnectedFragment(this);

        accountBaseFragment.setFragmentListener(this);

        // Thêm fragment vào FragmentManager
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_layout, homeBaseFragment, "home")
                .add(R.id.frame_layout, buyFragment, "buy")
                .add(R.id.frame_layout, postFragment, "post")
                .add(R.id.frame_layout,sellFragment,"sell")
                .add(R.id.frame_layout, accountBaseFragment, "account")
                .add(R.id.frame_layout,internetDisconnectedFragment,"internetConnection")
                .commit();

        // Ẩn tất cả các fragment, chỉ hiển thị fragment home ban đầu
        // Thêm HomeFragment vào FragmentContainerView
        // getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new HomeFragment()).commit();
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    showFragment(homeBaseFragment);
                    if (homeBaseFragment.isVisible()){
                        homeBaseFragment.replaceFragment(homeBaseFragment.homeFragment);
                        break;
                    }
                    else {
                        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameHomeBase);
                        if(currentFragment instanceof SearchFragment){
                            homeBaseFragment.replaceFragment(homeBaseFragment.searchFragment);
                        }
                        else if(currentFragment instanceof SearchResultFragment){
                            homeBaseFragment.replaceFragment(homeBaseFragment.searchResultFragment);
                        }
                        break;
                    }
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
                    showFragment(accountBaseFragment);
                    break;
            }
            return true;
        });
        broadcastReceiver = new ConnectionReceiver();
        registerNetworkBroadcast();
        currentFragment = homeBaseFragment;
        if(!broadcastReceiver.isConnected(getApplicationContext()))
            DisconnectedFragment();
        else
            ConnectedFragment(currentFragment);
    }
    public void showFragment(Fragment fragment) {

        currentFragment = fragment;
        if(!broadcastReceiver.isConnected(getApplicationContext()))
            DisconnectedFragment();
        else
            ConnectedFragment(fragment);
        // Ẩn bàn phím
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
    private void ConnectedFragment(Fragment fragment)
    {
        fragmentManager.beginTransaction()
                .hide(homeBaseFragment)
                .hide(buyFragment)
                .hide(postFragment)
                .hide(accountBaseFragment)
                .hide(sellFragment)
                .hide(internetDisconnectedFragment)
                .show(fragment)
                .commit();
    }
    private void DisconnectedFragment()
    {
        internetDisconnectedFragment.setPreviousFragment(currentFragment);
        fragmentManager.beginTransaction()
                .hide(homeBaseFragment)
                .hide(buyFragment)
                .hide(postFragment)
                .hide(accountBaseFragment)
                .hide(sellFragment)
                .show(internetDisconnectedFragment)
                .commit();
    }
    protected void registerNetworkBroadcast()
    {
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    protected void unregisterNetworkBroadcast()
    {
        try{
            unregisterReceiver(broadcastReceiver);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkBroadcast();
    }

    @Override
    public void OnLogoutListener() {
        buyFragment.DisplayRequireLoginView();
        sellFragment.DisplayRequireLoginView();
        postFragment.DisplayRequireLoginView();
    }
}