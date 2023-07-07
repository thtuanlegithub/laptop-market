package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.laptop_market.R;
import com.example.laptop_market.model.post.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    public AccountBaseFragment accountBaseFragment;
    private Button btnProfileBack;
    private ImageView imgProfileAvatar;
    private FragmentManager fragmentManager;
    private FrameLayout frameLayoutProfile;
    private List<Fragment> fragmentList;
    private PostFragment fragmentPost;
    private BottomNavigationView navProfilePost;
    private ViewPager2 viewPagerPost;
    private FragmentStateAdapter fragmentStateAdapter;
    private int currentSelectedItem = 0;
    public ProfileFragment(){

    }
    public ProfileFragment(AccountBaseFragment accountBaseFragment){
        this.accountBaseFragment = accountBaseFragment;
        fragmentList = new ArrayList<>();
        fragmentList.add(new PostActiveFragment());
        fragmentList.add(new PostInactiveFragment());

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        imgProfileAvatar = view.findViewById(R.id.imgProfileAvatar);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPagerPost = view.findViewById(R.id.viewPagerProfilePost);
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
        navProfilePost = view.findViewById(R.id.navProfilePost);
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

        btnProfileBack = view.findViewById(R.id.btnProfileBack);
        btnProfileBack.setOnClickListener(v -> {
            accountBaseFragment.replaceFragment(accountBaseFragment.accountFragment);
        });
    }

}