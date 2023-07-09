package com.example.laptop_market.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IFragmentListener;
import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.presenter.fragments.BuyFragmentPresenter;
import com.example.laptop_market.utils.elses.FragmentActivityType;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.view.activities.LoginActivity;
import com.example.laptop_market.view.activities.MainActivity;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;


public class BuyFragment extends Fragment implements IOrderContract.View.BuyFragmentView {
    private List<Fragment> fragmentList;
    private ViewPager2 viewPagerBuy;
    private BottomNavigationView navBuy;
    private FragmentStateAdapter fragmentStateAdapter;
    private int currentSelectedItem = 0;
    private GridLayout gridRequireLoginForBuy;
    private Button btnRequireLoginForBuy;
    private ActivityResultLauncher<Intent> loginLauncher;
    private IOrderContract.Presenter.BuyFragmentPresenter buyFragmentPresenter;
    private boolean applySlideTransition = true;
    private BuyProcessingFragment buyProcessingFragment;
    private BuyDeliveringFragment buyDeliveringFragment;
    private BuyFinishFragment buyFinishFragment;
    private BuyCancelFragment buyCancelFragment;
    public BuyFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set activity result when Login from BuyFragment
        /*loginLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        DisplayManageOrderView();
                    }
                }
        );*/
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        buyFragmentPresenter = new BuyFragmentPresenter(this, this.getContext());
        View view = inflater.inflate(R.layout.fragment_buy, container, false);
        gridRequireLoginForBuy = view.findViewById(R.id.gridRequireLoginForBuy);

        // Create objects
        buyProcessingFragment = new BuyProcessingFragment();
        buyDeliveringFragment = new BuyDeliveringFragment();
        buyFinishFragment = new BuyFinishFragment();
        buyCancelFragment = new BuyCancelFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(buyProcessingFragment);
        fragmentList.add(buyDeliveringFragment);
        fragmentList.add(buyFinishFragment);
        fragmentList.add(buyCancelFragment);
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

        btnRequireLoginForBuy = view.findViewById(R.id.btnRequireLoginForBuy);
        btnRequireLoginForBuy.setOnClickListener(view1 -> {
            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            PreferenceManager preferenceManager = new PreferenceManager(getContext());
            preferenceManager.putInt(FragmentActivityType.FRAGMENT_ACTIVITY, FragmentActivityType.MANAGE_BUYING_ORDER);
            startActivity(intent);
            getActivity().finish();
            //loginLauncher.launch(intent);
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void DisplayRequireLoginView() {
        gridRequireLoginForBuy.setVisibility(View.VISIBLE);
    }

    @Override
    public void DisplayManageOrderView() {
        gridRequireLoginForBuy.setVisibility(View.GONE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            boolean shouldApplySlideTransition = data.getBooleanExtra("applySlideTransition", false);
            if (shouldApplySlideTransition) {
                applySlideTransition = true;
            }
            else{
                applySlideTransition = false;
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (applySlideTransition) {
            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        buyFragmentPresenter.LoadManageBuyOrder();
    }
}