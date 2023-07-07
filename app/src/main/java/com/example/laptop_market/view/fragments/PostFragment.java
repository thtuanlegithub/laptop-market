package com.example.laptop_market.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.presenter.fragments.PostFragmentPresenter;
import com.example.laptop_market.utils.elses.FragmentActivityType;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.view.activities.LoginActivity;
import com.example.laptop_market.view.activities.NewPostActivity;
import com.example.laptop_market.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment implements IPostContract.View.PostFragmentView {
    private FloatingActionButton fabNewPost;
    private List<Fragment> fragmentList;
    private ViewPager2 viewPagerPost;
    private BottomNavigationView navPost;
    private FragmentStateAdapter fragmentStateAdapter;
    private int currentSelectedItem = 0;
    private IPostContract.Presenter.PostFragmentPresenter postFragmentPresenter;
    private GridLayout gridRequireLoginForPost;
    private Button btnRequireLoginForPost;
    private ActivityResultLauncher<Intent> loginLauncher;
    public PostFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set activity result when Login from BuyFragment
        loginLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        DisplayManagePostView();
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        postFragmentPresenter = new PostFragmentPresenter(getContext(),this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        gridRequireLoginForPost = view.findViewById(R.id.gridRequireLoginForPost);
        btnRequireLoginForPost = view.findViewById(R.id.btnRequireLoginForPost);
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

        // Create new post
        fabNewPost = view.findViewById(R.id.fabNewPost);
        fabNewPost.setOnClickListener(view1 -> {
            postFragmentPresenter.CreateNewPost();
        });
        btnRequireLoginForPost.setOnClickListener(view1 -> {
            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            PreferenceManager preferenceManager = new PreferenceManager(getContext());
            preferenceManager.putInt(FragmentActivityType.FRAGMENT_ACTIVITY, FragmentActivityType.MANAGE_POST);
            loginLauncher.launch(intent);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        postFragmentPresenter.LoadManagePost();
    }

    @Override
    public void CreateNewPost() {
        Intent intent = new Intent(getContext(), NewPostActivity.class);
        startActivity(intent);
    }

    @Override
    public void LoginAccount() {
        Intent intent = new Intent(this.getActivity(), LoginActivity.class);
        PreferenceManager preferenceManager = new PreferenceManager(getContext());
        preferenceManager.putInt(FragmentActivityType.FRAGMENT_ACTIVITY, FragmentActivityType.NEW_POST_ACTIVITY);
        startActivity(intent);
    }

    @Override
    public void DisplayRequireLoginView() {
        gridRequireLoginForPost.setVisibility(View.VISIBLE);
    }

    @Override
    public void DisplayManagePostView() {
        gridRequireLoginForPost.setVisibility(View.GONE);
    }
}