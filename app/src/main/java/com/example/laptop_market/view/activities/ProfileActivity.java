package com.example.laptop_market.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.laptop_market.R;
import com.example.laptop_market.view.fragments.PostActiveFragment;
import com.example.laptop_market.view.fragments.PostInactiveFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private Button btnProfileBack;
    private ImageView imgProfileAvatar;
    private List<Fragment> fragmentList;
    private FragmentStateAdapter fragmentStateAdapter;
    private BottomNavigationView navProfilePost;
    private ViewPager2 viewPagerPost;
    private int currentSelectedItem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fragmentList = new ArrayList<>();
        fragmentList.add(new PostActiveFragment());
        fragmentList.add(new PostInactiveFragment());
        //find view
        imgProfileAvatar = findViewById(R.id.imgProfileAvatar);
        viewPagerPost = findViewById(R.id.viewPagerProfilePost);
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
        navProfilePost = findViewById(R.id.navProfilePost);
        navProfilePost.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.postActive && currentSelectedItem != 0) {
                viewPagerPost.setCurrentItem(0);
                currentSelectedItem = 0;
            } else if (itemId == R.id.postInactive && currentSelectedItem != 1) {
                viewPagerPost.setCurrentItem(1);
                currentSelectedItem = 1;
            }

            return true;
        });
        viewPagerPost.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                navProfilePost.getMenu().getItem(position).setChecked(true);
                currentSelectedItem = position;
            }
        });

        // circle avatar of seller
        Glide.with(this)
                .load(R.drawable.slide_show1)
                .apply(RequestOptions.circleCropTransform())
                .into(imgProfileAvatar);

        btnProfileBack = findViewById(R.id.btnProfileBack);
        btnProfileBack.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("applySlideTransition", true);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }

}