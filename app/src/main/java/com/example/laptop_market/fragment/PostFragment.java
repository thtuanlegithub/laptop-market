package com.example.laptop_market.fragment;

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
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {
    private List<Fragment> fragmentList;
    private ViewPager2 viewPagerPost;
    private BottomNavigationView navPost;
    private FragmentStateAdapter fragmentStateAdapter;
    private int currentSelectedItem = 0;
    public PostFragment() {
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
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        fragmentList = new ArrayList<>();
        fragmentList.add(new PostActiveFragment());
        fragmentList.add(new PostInactiveFragment());
        viewPagerPost = view.findViewById(R.id.viewPagerPost);
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
        viewPagerPost.setAdapter(fragmentStateAdapter);
        navPost = view.findViewById(R.id.navPost);
        navPost.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.postActive && currentSelectedItem != 0) {
                    viewPagerPost.setCurrentItem(0);
                    currentSelectedItem = 0;
                } else if (itemId == R.id.postInactive && currentSelectedItem != 1) {
                    viewPagerPost.setCurrentItem(1);
                    currentSelectedItem = 1;
                }

                return true;
            }
        });
        viewPagerPost.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                navPost.getMenu().getItem(position).setChecked(true);
                currentSelectedItem = position;
            }
        });


        return view;
    }
}