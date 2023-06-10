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
 * Use the {@link SellFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellFragment extends Fragment {
    private List<Fragment> fragmentList;
    private ViewPager2 viewPagerSell;
    private BottomNavigationView navSell;
    private FragmentStateAdapter fragmentStateAdapter;
    private int currentSelectedItem = 0;
    public SellFragment() {
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
        View view = inflater.inflate(R.layout.fragment_sell, container, false);
        fragmentList = new ArrayList<>();
        fragmentList.add(new SellDeliveringFragment());
        fragmentList.add(new SellFinishFragment());
        fragmentList.add(new SellCancelFragment());
        viewPagerSell = view.findViewById(R.id.viewPagerSell);
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
        viewPagerSell.setAdapter(fragmentStateAdapter);
        navSell = view.findViewById(R.id.navSell);
        navSell.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.sellDelivering && currentSelectedItem != 0) {
                    viewPagerSell.setCurrentItem(0);
                    currentSelectedItem = 0;
                } else if (itemId == R.id.sellFinish && currentSelectedItem != 1) {
                    viewPagerSell.setCurrentItem(1);
                    currentSelectedItem = 1;
                } else if (itemId == R.id.sellCancel && currentSelectedItem != 2) {
                    viewPagerSell.setCurrentItem(2);
                    currentSelectedItem = 2;
                }

                return true;
            }
        });
        viewPagerSell.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                navSell.getMenu().getItem(position).setChecked(true);
                currentSelectedItem = position;
            }
        });


        return view;
    }
}