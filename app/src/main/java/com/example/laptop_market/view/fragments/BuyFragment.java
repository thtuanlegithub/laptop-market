package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop_market.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyFragment extends Fragment {
    private List<Fragment> fragmentList;
    private ViewPager2 viewPagerBuy;
    private BottomNavigationView navBuy;
    private FragmentStateAdapter fragmentStateAdapter;
    private int currentSelectedItem = 0;
    public BuyFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy, container, false);

        fragmentList = new ArrayList<>();
        fragmentList.add(new BuyProcessingFragment());
        fragmentList.add(new BuyDeliveringFragment());
        fragmentList.add(new BuyFinishFragment());
        fragmentList.add(new BuyCancelFragment());
        viewPagerBuy = view.findViewById(R.id.viewPagerBuy);
        fragmentStateAdapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        };
        viewPagerBuy.setAdapter(fragmentStateAdapter);
        navBuy = view.findViewById(R.id.navBuy);
        navBuy.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.buyProcessing && currentSelectedItem != 0){
                    viewPagerBuy.setCurrentItem(0);
                    currentSelectedItem = 0;
                }
                if (itemId == R.id.buyDelivering && currentSelectedItem != 1) {
                    viewPagerBuy.setCurrentItem(1);
                    currentSelectedItem = 1;
                } else if (itemId == R.id.buyFinish && currentSelectedItem != 2) {
                    viewPagerBuy.setCurrentItem(2);
                    currentSelectedItem = 2;
                }else if (itemId == R.id.buyCancel && currentSelectedItem != 3) {
                    viewPagerBuy.setCurrentItem(3);
                    currentSelectedItem = 3;
                }
                return true;
            }
        });
        viewPagerBuy.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                navBuy.getMenu().getItem(position).setChecked(true);
                currentSelectedItem = position;
            }
        });

        return view;
    }
}